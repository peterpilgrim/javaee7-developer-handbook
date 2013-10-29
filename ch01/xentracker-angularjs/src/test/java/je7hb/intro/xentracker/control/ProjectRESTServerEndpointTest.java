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
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.File;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static je7hb.intro.xentracker.FixtureUtils.createProjectAndTasks;
import static javax.ws.rs.core.MediaType.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A unit test ProjectRESTServerEndpointTest to verify the operation of ProjectRESTServerEndpointTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ProjectRESTServerEndpointTest {

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
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("classes/beans.xml"));
        System.out.println(war.toString(true));
        return war;
    }

    @EJB
    ProjectTaskService service;

    private static final JsonBuilderFactory bf = Json.createBuilderFactory(null);

    @Test
    public void shouldInvokePOSTcreateProjectWithTasks() throws Exception {

        JsonObject input = bf.createObjectBuilder()
            .add("name", "Java Performance Tuning")
            .add("headline", "Goldenrule")
            .add("description", "Measure before, measure after")
            .add("tasks",
                bf.createArrayBuilder()
                    .add(
                            bf.createObjectBuilder()
                                    .add("name", "Find the Common Denominator")
                                    .add("targetDate", "23-July-2018")
                                    .add("completed", JsonValue.FALSE)
                                    .build())
                    .add(
                            bf.createObjectBuilder()
                                    .add("name", "Decide on the Metrics")
                                    .add("targetDate", "24-July-2018")
                                    .add("completed", JsonValue.FALSE)
                                    .build())
                    .build()
            )
            .build();

        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8181/xentracker/rest/projects/item");

        Response response = target.request().post(
            Entity.entity(
                input, APPLICATION_JSON_TYPE) );
        assertThat(response.getStatus(), is(200));

        String text = response.readEntity( String.class );
        System.out.printf(">>------ text = %s\n", text);
        assertNotNull(text);
    }

    @Test
    public void shouldInvokeGETretrieveProjectWithProjectId() throws Exception {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<Project> projects = service.findAllProjects();
        Thread.sleep(1000);

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xentracker/rest/projects/item/" + proj1.getId());
        Response response = target.request().get();
        assertThat(response.getStatus(), is(200));
        String text = response.readEntity( String.class );
        System.out.printf(">>====== text = %s\n", text);
        assertNotNull(text);
    }


    @Test
    public void shouldInvokeAsynchronousServerGET() throws Exception {
        Project proj1 = createProjectAndTasks(3);
        Project proj2 = createProjectAndTasks(2);
        Project proj3 = createProjectAndTasks(1);
        service.saveProject(proj1);
        service.saveProject(proj2);
        service.saveProject(proj3);

        // Force a flush to the database?!
        List<Project> projects = service.findAllProjects();
        Thread.sleep(1000);

        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8181/xentracker/rest/projects/list");
        Future<Response> future =
                target.request().async().get();
        assertNotNull(future);
        Response response = future.get(3, TimeUnit.SECONDS );
        assertThat(response.getStatus(), is(200));
        String text = response.readEntity( String.class );
        System.out.printf(">>====== text = %s\n", text);
        assertNotNull(text);
    }

    @Test
    public void shouldInvokePOSTWithAddNewTaskToProject() throws Exception {
        Project proj1 = createProjectAndTasks(1);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<Project> projects = service.findAllProjects();
        Thread.sleep(1000);

        JsonObject input = bf.createObjectBuilder()
            .add("name", "Digital Holographic-SLR Camera")
            .add("targetDate", "16-January-2018")
            .add("completed", JsonValue.FALSE)
            .build();

        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8181/xentracker/rest/projects/item/" + proj1.getId() + "/task");
        Response response = target.request().post(
            Entity.entity(
            input, APPLICATION_JSON_TYPE) );
        assertThat(response.getStatus(), is(200));
        String text = response.readEntity( String.class );
        System.out.printf(">>====== text = %s\n", text);
        assertNotNull(text);
    }

    @Test
    public void shouldInvokePUTWithAddUpdateTaskToProject() throws Exception {
        Project proj1 = createProjectAndTasks(0);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<Project> projects = service.findAllProjects();
        Thread.sleep(1000);

        JsonObject input1 = bf.createObjectBuilder()
                .add("name", "Fireside Chat")
                .add("targetDate", "21-May-2014")
                .add("completed", JsonValue.FALSE)
                .build();

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xentracker/rest/projects/item/"+proj1.getId()+"/task");
        Response response = target.request().post(
                Entity.entity(
                        input1, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
        JsonObject output1 = response.readEntity( JsonObject.class );
        System.out.printf(">>====== output1 = %s\n", output1);
        assertNotNull(output1);

        assertThat( output1.getJsonArray("tasks").size(), is(1));
        JsonObject json = output1.getJsonArray("tasks").getJsonObject(0);
        int taskId = json.getInt("id");
        assertTrue( taskId > 0 );
        assertThat( "Fireside Chat", is(json.getString("name")));
        assertThat( "2014-05-21", is(json.getString("targetDate")));
        assertFalse( json.getBoolean("completed")) ;
        JsonObject input2 = bf.createObjectBuilder()
                .add("id", taskId )
                .add("name", "JavaOne USA")
                .add("targetDate", "29-Sep-2014")
                .add("completed", JsonValue.TRUE)
                .build();

        target = ClientBuilder.newClient()
                .target("http://localhost:8181/xentracker/rest/projects/item/"+proj1.getId()+"/task/"+taskId);
        response = target.request().put(
                Entity.entity(
                        input2, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
        JsonObject output2 = response.readEntity( JsonObject.class );
        System.out.printf(">>====== output2 = %s\n", output2);
        assertNotNull(output2);

        assertThat( output2.getJsonArray("tasks").size(), is(1));
        json = output2.getJsonArray("tasks").getJsonObject(0);
        taskId = json.getInt("id");
        assertTrue(taskId > 0);
        assertThat( "JavaOne USA", is(json.getString("name")));
        assertThat( "2014-09-29", is(json.getString("targetDate")));
        assertTrue( json.getBoolean("completed"));
    }

    @Test
    public void shouldInvokeDELETEWithAddRemoveTaskFromProject() throws Exception {
        Project proj1 = createProjectAndTasks(0);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<Project> projects = service.findAllProjects();
        Thread.sleep(1000);

        JsonObject input1 = bf.createObjectBuilder()
                .add("name", "Make money now")
                .add("targetDate", "14-Jul-2015")
                .add("completed", JsonValue.FALSE)
                .build();

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xentracker/rest/projects/item/"+proj1.getId()+"/task");
        Response response = target.request().post(
                Entity.entity(
                        input1, APPLICATION_JSON_TYPE));
        assertNotNull(response);
        JsonObject output1 = response.readEntity( JsonObject.class );
        System.out.printf(">>====== output1 = %s\n", output1);
        assertNotNull(output1);

        JsonObject json = output1.getJsonArray("tasks").getJsonObject(0);
        int taskId = json.getInt("id");
        assertTrue( taskId > 0 );
        assertThat( "Make money now", is(json.getString("name")));
        assertThat( "2015-07-14", is(json.getString("targetDate")));
        assertFalse( json.getBoolean("completed")) ;

        target = ClientBuilder.newClient()
                .target("http://localhost:8181/xentracker/rest/projects/item/" + proj1.getId() + "/task/" + taskId);
        response = target.request().delete();
        assertThat( response.getStatus(), is(200));

        JsonObject output2 = response.readEntity( JsonObject.class );
        assertThat( output2.getJsonArray("tasks").size(), is(0));
    }

    @Test
    public void shouldInvokePUTeditProjectDetails() throws Exception {
        Project proj1 = createProjectAndTasks(1);
        service.saveProject(proj1);

        // Force a flush to the database?!
        List<Project> projects = service.findAllProjects();
        Thread.sleep(1000);

        JsonObject input1 = bf.createObjectBuilder()
                .add("id", proj1.getId())
                .add("name", "Visit Client")
                .add("headline", "Essential face-to-face meeting")
                .add("description", "Apple and oranges")
                /* We don't need the Task JSON object here */
                .build();

        WebTarget target = ClientBuilder.newClient()
                .target("http://localhost:8181/xentracker/rest/projects/item/" + proj1.getId());
        Response response = target.request().put(
                Entity.entity(
                        input1, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));

        JsonObject output1 = response.readEntity(JsonObject.class);
        assertThat( "Visit Client", is(output1.getString("name")));
        assertThat( "Essential face-to-face meeting", is(output1.getString("headline")));
        assertThat( "Apple and oranges", is(output1.getString("description")));
        assertThat(output1.getJsonArray("tasks").size(), is(1));
    }

}