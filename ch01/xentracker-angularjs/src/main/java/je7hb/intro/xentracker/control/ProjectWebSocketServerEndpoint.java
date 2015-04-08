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

import javax.ejb.*;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type ProjectWebSocketServerEndpoint
 *
 * @author Peter Pilgrim (peter)
 */
@ServerEndpoint("/sockets")
@Stateless
public class ProjectWebSocketServerEndpoint {
    static SimpleDateFormat FMT =
        new SimpleDateFormat("dd-MMM-yyyy");
    @Inject ProjectTaskService service;

    @OnMessage
    public String retrieveProjectAndTasks(String message) {
        final int projectId = Integer.parseInt(message.trim());
        final List<Project> projects =
            service.findProjectById(projectId);
        final StringWriter swriter = new StringWriter();

        final JsonGeneratorFactory factory =
            Json.createGeneratorFactory(
                new HashMap<String, Object>(){{
                    put(JsonGenerator.PRETTY_PRINTING, true);
                }});
        final JsonGenerator generator = factory.createGenerator(swriter);

        generator.writeStartArray();
        for ( Project project: projects ) {
            generator.writeStartObject()
                .write("id", project.getId())
                .write("name", project.getName())
                .writeStartArray("tasks");

            for ( Task task: project.getTasks()) {
                generator.writeStartObject()
                    .write("id", task.getId())
                    .write("name", task.getName())
                    .write("targetDate",
                        task.getTargetDate() == null ? "" :
                        FMT.format(task.getTargetDate()))
                    .write("completed", task.isCompleted())
                    .writeEnd();
            }
            generator.writeEnd().writeEnd();
        }
        generator.writeEnd().close();

        return swriter.toString();
    }

    @OnOpen
    public void open( Session session ) {
        System.out.printf("%s.open( session=%s)\n",
                getClass().getSimpleName(), session );
    }

    @OnClose
    public void close( Session session ) {
        System.out.printf("%s.close( session=%s)\n",
                getClass().getSimpleName(), session );
    }

    @OnError
    public void error( Session session, Throwable error ){
        System.err.printf("%s.onError( session=%s, error=%s )\n",
                getClass().getSimpleName(), session, error);
        error.printStackTrace(System.err);
    }
}
