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
import je7hb.intro.xentracker.entity.Project;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

/**
 * The type ProjectWebSocketRetrieveEndpoint
 *
 * @author Peter Pilgrim (peter)
 */
@ServerEndpoint("/retrieve-all-projects")
@Stateless
public class ProjectWebSocketRetrieveEndpoint {
    @Inject ProjectTaskService service;

    static JsonGeneratorFactory jsonGeneratorFactory
            = Json.createGeneratorFactory(
            new HashMap<String, Object>() {{
                put(JsonGenerator.PRETTY_PRINTING, true);
            }});

    @OnMessage
    public String updateProjectTask(String message) {
        final int projectId = Integer.parseInt(message.trim());
        List<Project> projects =
            service.findProjectById(projectId);
        final StringWriter swriter = new StringWriter();
        final JsonGenerator generator = jsonGeneratorFactory.createGenerator(swriter);
        ProjectHelper.generateProjectsAsJson(generator, projects).close();
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
