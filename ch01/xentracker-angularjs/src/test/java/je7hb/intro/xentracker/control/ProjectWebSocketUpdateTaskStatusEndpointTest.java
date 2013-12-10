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

import je7hb.intro.xentracker.StringHelper;
import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.net.URI;
import java.util.List;

import static je7hb.intro.xentracker.FixtureUtils.createProjectAndTasks;
import static org.junit.Assert.*;

/**
 * A unit test ProjectWebSocketRetrieveEndpointTest to verify the operation of ProjectWebSocketRetrieveEndpointTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ProjectWebSocketUpdateTaskStatusEndpointTest {

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "xentracker.war")
                .addPackage(Project.class.getPackage())
                .addPackage(ProjectWebSocketUpdateStatusTaskEndpoint.class.getPackage())
                .addPackage(ProjectTaskService.class.getPackage())
                .addClasses(StringHelper.class)
                .addAsWebInfResource(
                        "test-persistence.xml",
                        "classes/META-INF/persistence.xml")
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("classes/beans.xml"));
        System.out.println(war.toString(true));
        return war;
    }

    @EJB
    ProjectTaskService service;

    @Test
    public void invokeEndpointChangeProjectTaskStatus() throws Exception {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);

        assertFalse(proj1.getTasks().get(0).isCompleted());
        StringWriter swriter = new StringWriter();
        JsonGenerator generator = Json.createGenerator(swriter);
        generator.writeStartObject()
            .write("projectId", proj1.getId())
            .write("taskId", proj1.getTasks().get(0).getId())
            .write("completed", true)
            .writeEnd().close();

        System.out.printf("****  SENDING JSON OVER TO WEBSOCKET ****\n" +
                ">>>>  swriter=%s\n", swriter.toString());

        System.out.printf("****  START  ****\n");
        URI uri = URI.create("ws://localhost:8181/xentracker/update-task-status");
        System.out.printf("uri=%s\n", uri ) ;
        WebSocketClientTesterEndpoint client = new WebSocketClientTesterEndpoint(
                uri.toString(), swriter.toString());

        client.makeConnection();
        System.out.printf("****  WAIT   ****\n");

        String text = client.getReceivedMessage( 3000 );
//        assertEquals("ECHO: "+message,actual);
        System.out.printf(">>>> text=%s\n", text);
        assertNotNull(text);
        List<Project> projects = service.findProjectById(proj1.getId());
        assertTrue(projects.get(0).getTasks().get(0).isCompleted());
        assertEquals("OK",text);
        System.out.printf("****  DONE   ****\n");
    }
}
