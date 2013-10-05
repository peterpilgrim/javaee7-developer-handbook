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
import java.net.URI;

import static je7hb.intro.xentracker.FixtureUtils.createProjectAndTasks;
import static org.junit.Assert.*;

/**
 * A unit test ProjectWebSocketServerEndpointTest to verify the operation of ProjectWebSocketServerEndpointTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ProjectWebSocketServerEndpointTest {

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "xentracker.war")
                .addPackage(Project.class.getPackage())
                .addPackage(ProjectWebSocketServerEndpointTest.class.getPackage())
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

//    @ArquillianResource
//    private URL baseURL;

    @EJB
    ProjectTaskService service;

    @Test
    public void shouldInvokeServerEndpoint() throws Exception {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);
        String message = Integer.toString(proj1.getId());

        System.out.printf("****  START  ****\n");
        URI uri = URI.create("ws://localhost:8181/xentracker/sockets");
        System.out.printf("uri=%s\n", uri ) ;
        WebSocketClientTesterEndpoint client = new WebSocketClientTesterEndpoint(
                uri.toString() , message );

        client.makeConnection();
        System.out.printf("****  WAIT   ****\n");

        String json = client.getReceivedMessage( 3000 );
//        assertEquals("ECHO: "+message,actual);
        System.out.printf(">>>> json=%s\n", json);
        assertNotNull(json);

        System.out.printf("****  DONE   ****\n");
    }
}
