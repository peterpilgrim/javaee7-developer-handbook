/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.*;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.json.*;
import javax.json.stream.*;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.ws.rs.core.MediaType.*;

/**
 * The type ProjectRESTServerEndpoint
 *
 * @author Peter Pilgrim (peter)
 */
@Path("/projects/")
@Stateless
public class ProjectRESTServerEndpoint {

    static JsonGeneratorFactory jsonGeneratorFactory
        = Json.createGeneratorFactory(
            new HashMap<String, Object>() {{
                put(JsonGenerator.PRETTY_PRINTING, true);
            }});

    @Inject ProjectTaskService service;

    @GET
    @Path("/item")
    @Produces(APPLICATION_JSON)
    public String retrieveProject(
        @PathParam("id") @DefaultValue("0") int projectId ) {
        List<Project> projects =
                service.findProjectById(projectId);
        StringWriter swriter = new StringWriter();
        JsonGenerator generator
            = jsonGeneratorFactory.createGenerator(swriter);
        ProjectHelper.generateProjectsAsJson(generator, projects).close();
        return swriter.toString();
    }

    @POST
    @Path("/item")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String createProject( JsonObject projectObject )
            throws Exception {
        Project project = new Project( projectObject.getString("name"));
        JsonArray tasksArray = projectObject.getJsonArray("tasks");
        if ( tasksArray != null ) {
            for ( int j=0; j<tasksArray.size(); ++j ) {
                JsonObject taskObject = tasksArray.getJsonObject(j);
                Task task = new Task(
                    taskObject.getString("name"),
                    ( taskObject.containsKey("targetDate") ?
                            ProjectHelper.FMT.parse(taskObject.getString("targetDate")) :
                       null ),
                    taskObject.getBoolean("completed"));
                project.addTask(task);
            }
        }

        service.saveProject(project);
        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
            jsonGeneratorFactory.createGenerator(swriter);
        ProjectHelper.writeProjectAsJson(generator, project).close();
        return swriter.toString();
    }

    @Resource(name="concurrent/LongRunningTasksExecutor")
    ManagedExecutorService executor;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public void getProjectList(
        @Suspended final AsyncResponse asyncResponse) {
        System.out.printf("=======> %s.getProjectList() %s asyncResponse=%s\n",
                getClass().getSimpleName(), Thread.currentThread(), asyncResponse );

        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.printf("========>> %s.getProjectList() Executable Task %s asyncResponse=%s\n",
                        getClass().getSimpleName(), Thread.currentThread(), asyncResponse);
                List<Project> projects = service.findAllProjects();
                StringWriter swriter = new StringWriter();
                JsonGenerator generator
                    = jsonGeneratorFactory.createGenerator(swriter);
                ProjectHelper.generateProjectsAsJson(generator, projects).close();
                System.out.printf("========>> Sending swriter=[%s]\n", swriter.toString());
                Response response =
                        Response.ok(swriter.toString()).build();
                asyncResponse.resume(response);
            }
        });

        // We add this slight delay to ensure the Arquillian integration unit test receives the result.
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
