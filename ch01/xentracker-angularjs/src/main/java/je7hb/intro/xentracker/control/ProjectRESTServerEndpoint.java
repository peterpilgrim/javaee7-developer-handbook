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
import java.text.ParseException;
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
    static SimpleDateFormat FMT =
        new SimpleDateFormat("dd-MMM-yyyy");
    static SimpleDateFormat FMT2 =
            new SimpleDateFormat("yyyy-MM-dd");

    static JsonGeneratorFactory jsonGeneratorFactory
        = Json.createGeneratorFactory(
            new HashMap<String, Object>() {{
                put(JsonGenerator.PRETTY_PRINTING, true);
            }});

    @Inject ProjectTaskService service;

    @GET
    @Path("/item/{id}")
    @Produces(APPLICATION_JSON)
    public String retrieveProject(
        @PathParam("id") int projectId ) {
        if (projectId < 1)
            throw new RuntimeException(
                    "Invalid projectId:["+projectId+"] supplied");

        List<Project> projects = service.findProjectById( projectId );
        if ( projects.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with projectId:["+projectId+"]");
        }

        StringWriter swriter = new StringWriter();
        JsonGenerator generator
            = jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, projects.get(0)).close();
        return swriter.toString();
    }

    @POST
    @Path("/item")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String createProject( JsonObject projectObject )
            throws Exception {
        Project project = new Project( projectObject.getString("name"),
            projectObject.getString("headline"),
            projectObject.getString("description"));

        JsonArray tasksArray = projectObject.getJsonArray("tasks");
        if ( tasksArray != null ) {
            for ( int j=0; j<tasksArray.size(); ++j ) {
                JsonObject taskObject = tasksArray.getJsonObject(j);
                Task task = new Task(
                    taskObject.getString("name"),
                    ( taskObject.containsKey("targetDate") ?
                       FMT.parse(taskObject.getString("targetDate")) :
                       null ),
                    taskObject.getBoolean("completed"));
                project.addTask(task);
                task.setProject(project);
            }
        }

        service.saveProject(project);
        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
            jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, project).close();
        return swriter.toString();
    }

    @PUT
    @Path("/item/{projectId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String updateProject(
            @PathParam("projectId") int projectId,
            JsonObject projectObject )
            throws Exception {
        if (projectId < 1)
            throw new RuntimeException(
                    "Invalid projectId:["+projectId+"] supplied");

        List<Project> projects = service.findProjectById( projectId );
        if ( projects.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with projectId:["+projectId+"]");
        }

        Project project = projects.get(0);
        project.setName(projectObject.getString("name"));
        project.setHeadline(projectObject.getString("headline"));
        project.setDescription(projectObject.getString("description"));

        service.saveProject(project);
        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, project).close();
        return swriter.toString();
    }

    @POST
    @Path("/item/{projectId}/task")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String createNewTaskOnProject(
            @PathParam("projectId") int projectId,
            JsonObject taskObject )
            throws Exception
    {
        System.out.printf("createNewTaskOnProject( %d, %s )\n", projectId, taskObject);
        if (projectId < 1)
            throw new RuntimeException(
                    "Invalid projectId:["+projectId+"] supplied");

        List<Project> projects = service.findProjectById( projectId );
        if ( projects.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with projectId:["+projectId+"]");
        }

        Project project = projects.get(0);
        Task task = new Task(
                taskObject.getString("name"),
                ( taskObject.containsKey("targetDate") ?
                        convertToDate(taskObject.getString("targetDate")) :
                        null ),
                ( taskObject.containsKey("completed")) ?
                    taskObject.getBoolean("completed") : false );
        project.addTask(task);
        service.saveProject(project);

        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, project).close();
        return swriter.toString();
    }

    @PUT
    @Path("/item/{projectId}/task/{taskId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String updateTaskOnProject(
            @PathParam("projectId") int projectId,
            @PathParam("taskId") int taskId,
            JsonObject taskObject )
            throws Exception
    {
        System.out.printf("updateTaskOnProject( %d, %d, %s )\n", projectId, taskId, taskObject);
        if (projectId < 1)
            throw new RuntimeException(
                    "Invalid projectId:["+projectId+"] supplied");

        List<Project> projects = service.findProjectById( projectId );
        if ( projects.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with projectId:["+projectId+"]");
        }

        Project project = projects.get(0);
        for ( Task task: project.getTasks()) {
            if ( task.getId().equals(taskId )) {
                task.setName( taskObject.getString("name") );
                task.setTargetDate( taskObject.containsKey("targetDate") ?
                        convertToDate(taskObject.getString("targetDate")) :
                        null );
                task.setCompleted( taskObject.containsKey("completed") ?
                        taskObject.getBoolean("completed") : false );
            }
        }
        service.saveProject(project);

        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, project).close();
        return swriter.toString();
    }

    @DELETE
    @Path("/item/{projectId}/task/{taskId}")
    @Consumes( { APPLICATION_JSON, APPLICATION_XML, TEXT_PLAIN })
    @Produces(APPLICATION_JSON)
    public String removeTaskFromProject(
            @PathParam("projectId") int projectId,
            @PathParam("taskId") int taskId,
            JsonObject taskObject )
            throws Exception
    {
        // AngularJS requires additional consumption of XML in order to avoid 415 Unsupported Media Type
        // See this http://stackoverflow.com/questions/17379447/angularjs-and-jersey-rest-delete-operation-fails-with-415-status-code
        System.out.printf("removeTaskFromProject( %d, %d, %s )\n", projectId, taskId, taskObject);
        if (projectId < 1)
            throw new RuntimeException(
                    "Invalid projectId:["+projectId+"] supplied");

        List<Project> projects = service.findProjectById( projectId );
        if ( projects.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with projectId:["+projectId+"]");
        }

        Project project = projects.get(0);
        for ( Task task: project.getTasks()) {
            if ( task.getId().equals(taskId )) {
                project.removeTask(task);
                break;
            }
        }
        service.saveProject(project);

        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, project).close();
        return swriter.toString();
    }

    @GET
    @Path("/item/{projectId}/task/{taskId}/delete")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public String angularRemoveTaskFromProject(
            @PathParam("projectId") int projectId,
            @PathParam("taskId") int taskId,
            JsonObject taskObject )
            throws Exception
    {
        // AngularJS requires additional consumption of XML in order to avoid 415 Unsupported Media Type
        // See this http://stackoverflow.com/questions/17379447/angularjs-and-jersey-rest-delete-operation-fails-with-415-status-code
        System.out.printf("angularRemoveTaskFromProject( %d, %d, %s )\n", projectId, taskId, taskObject);
        if (projectId < 1)
            throw new RuntimeException(
                    "Invalid projectId:["+projectId+"] supplied");

        List<Project> projects = service.findProjectById( projectId );
        if ( projects.isEmpty() ) {
            throw new RuntimeException(
                    "No project was found with projectId:["+projectId+"]");
        }

        Project project = projects.get(0);
        for ( Task task: project.getTasks()) {
            if ( task.getId().equals(taskId )) {
                project.removeTask(task);
                break;
            }
        }
        service.saveProject(project);

        StringWriter swriter = new StringWriter();
        JsonGenerator generator =
                jsonGeneratorFactory.createGenerator(swriter);
        writeProjectAsJson(generator, project).close();
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
                try {
                    generateProjectsAsJson(generator, projects).close();
                    System.out.printf("========>> Sending swriter=[%s]\n", swriter.toString());
                    Response response =
                            Response.ok(swriter.toString()).build();
                    asyncResponse.resume(response);

                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

        // We add this slight delay to ensure the Arquillian integration unit test receives the result.
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Utility methods

    public static JsonGenerator generateProjectsAsJson( JsonGenerator generator, List<Project> projects ) {
        generator.writeStartArray();
        for ( Project project: projects ) {
            writeProjectAsJson(generator, project);
        }
        generator.writeEnd().close();
        return generator;
    }

    public static JsonGenerator writeProjectAsJson( JsonGenerator generator, Project project ) {
        generator.writeStartObject()
            .write("id", project.getId())
            .write("name", project.getName());
        if ( project.getHeadline() != null )
            generator.write("headline", project.getHeadline());
        if ( project.getDescription() != null )
            generator.write("description", project.getDescription());
        generator.writeStartArray("tasks");

        for ( Task task: project.getTasks()) {
            writeTaskAsJson(generator,task);
        }
        generator.writeEnd().writeEnd();
        return generator;
    }

    public static JsonGenerator writeTaskAsJson( JsonGenerator generator, Task task ) {
        generator.writeStartObject()
            .write("id", task.getId())
            .write("name", task.getName())
            .write("targetDate",
                    task.getTargetDate() == null ? "" :
                            FMT2.format(task.getTargetDate()))
            .write("completed", task.isCompleted())
            .write("projectId",
                    task.getProject() != null ? task.getProject().getId() : 0 )
            .writeEnd();
        return generator;
    }

    public static boolean convertToBoolean( String value ) {
        return convertToBoolean(value, false);
    }

    public static boolean convertToBoolean( String value, boolean defaultValue ) {
        if ( value == null )
            return defaultValue;
        value = value.trim();
        if ( value.length() == 0 )
            return defaultValue;
        if ( "true".equalsIgnoreCase(value) ||
                "yes".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) )
            return true;
        if ( "false".equalsIgnoreCase(value) ||
                "no".equalsIgnoreCase(value) || "0".equalsIgnoreCase(value) )
            return false;
        return defaultValue;
    }

    public static Date convertToDate( String value ) {
        if ( value == null )
            return null;
        value = value.trim();
        if ( value.length() == 0 )
            return null;

        Date date = null;
        try {
            date = FMT.parse(value);
        }
        catch (ParseException pe1) {
            try {
                date = FMT2.parse(value);
            }
            catch (ParseException pe2) {
                throw new RuntimeException(
                        "unable to parse date ["+value+"]");
            }
        }

        return date;
    }
}
