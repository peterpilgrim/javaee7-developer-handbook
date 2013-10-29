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

package je7hb.intro.xentracker.boundary;

import je7hb.intro.xentracker.StringHelper;
import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import static je7hb.intro.xentracker.FixtureUtils.*;

/**
 * A unit test ProjectTaskServiceTest to verify the operation of ProjectTaskServiceTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ProjectTaskServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
            .addPackage(Project.class.getPackage())
            .addPackage(ProjectTaskService.class.getPackage())
            .addClasses(StringHelper.class )
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
    public void shouldSaveProject() {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);
        System.out.printf("*****  proj1=%s\n", proj1);
        assertNotNull( proj1.getId());
        assertFalse( proj1.getTasks().isEmpty());
        assertThat( proj1.getTasks().size(), is(3));
        for ( Task task: proj1.getTasks()) {
            assertNotNull( task.getId());
            assertThat( task.getProject().getId(), is( proj1.getId()) );
        }
    }

    @Test
    public void shouldUpdateProject() {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);
        proj1.setName("Firebrand");
        proj1.getTasks().get(1).setName("Get skimmed milk");
        service.updateProject(proj1);
        System.out.printf("*****  proj1=%s\n", proj1);
    }

    @Test
    public void shouldFindProjectById() {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);

        List<Project> projects = service.findProjectById( proj1.getId());
        System.out.printf("*****  projects=%s\n", projects);
        assertThat(projects.size(), is(1));
        assertThat( projects.get(0).getTasks().size(), is(3));
    }

    @Test
    public void shouldFindAllProjectByIdOrderedByName() {
        service.clearDatabase();

        Project proj1 = createProjectAndTasks("Alice",3);
        Project proj2 = createProjectAndTasks("Ben",2);
        Project proj3 = createProjectAndTasks("Caroline",1);
        service.saveProject(proj1);
        service.saveProject(proj2);
        service.saveProject(proj3);

        List<Project> projects = service.findAllProjects();
        System.out.printf("*****  projects=%s\n", projects);
        assertThat(projects.size(), is(3));
        assertThat(projects.get(0).getTasks().size(), is(3));
        assertThat( projects.get(1).getTasks().size(), is(2));
        assertThat(projects.get(2).getTasks().size(), is(1));
    }

    @Test
    public void shouldFindTaskById() {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);

        List<Task> tasks = service.findTaskById( proj1.getTasks().get(1).getId());
        System.out.printf("*****  proj1=%s\n", proj1);
        System.out.printf("*****  tasks=%s\n", tasks);
        assertThat( tasks.size(), is(1));
        assertThat( tasks.get(0).getProject(), is(proj1));
    }

    @Test
    public void shouldTasksByProjectId() {
        Project proj1 = createProjectAndTasks(5);
        service.saveProject(proj1);

        List<Task> tasks = service.findTasksByProjectId(proj1.getId());
        System.out.printf("*****  proj1=%s\n", proj1);
        System.out.printf("*****  tasks=%s\n", tasks);
        assertThat( tasks.size(), is(5));
        for ( Task task: tasks ) {
            assertThat( task.getProject(), is(proj1));
        }
    }

    @Test
    public void shouldSaveAndRemoveProject() {
        Project proj1 = createProjectAndTasks(3);
        service.saveProject(proj1);
        service.removeProject(proj1);
        System.out.printf("*****  proj1=%s\n", proj1);
    }

    @Test
    public void shouldAnyProjects() {
        service.clearDatabase();
        List<Project> projects = service.findAllProjects();
        assertTrue( projects.isEmpty());
    }

    @Test
    public void shouldNotFindProjectsById() {
        service.clearDatabase();
        List<Project> projects = service.findProjectById(1);
        assertTrue( projects.isEmpty());
    }

    @Test
    public void shouldNotFindTasksById() {
        service.clearDatabase();
        List<Task> tasks= service.findTaskById(1);
        assertTrue( tasks.isEmpty());
    }

    @Test
    public void shouldNotFindTasksByProjectId() {
        service.clearDatabase();
        List<Task> tasks= service.findTasksByProjectId(1);
        assertTrue( tasks.isEmpty());
    }

    public static Date getFutureRandomDate() {
        return getFutureRandomDate( new Date() );
    }

    public static Date getFutureRandomDate( Date now ) {
        int days = (int)(Math.random() * 14 + 1);
        return new Date( days * 86400L * 1000L + now.getTime());
    }

    @Test
    public void createData() {
        try {
            createInitialProjectData();
        }
        catch (Throwable t) {
            System.err.println(t.getMessage());
            throw t;
        }
    }

    public void createInitialProjectData() {
        Project project1 = new Project("Technology Presentation");
        project1.addTask( new Task("Design concept", getFutureRandomDate(), true ));
        project1.addTask( new Task("Write slides", getFutureRandomDate(), false ));
        project1.addTask( new Task("Talk to the organizer", getFutureRandomDate(), false ));
        project1.addTask( new Task("Prepare demos", getFutureRandomDate(), false ));

        service.saveProject(project1);

        Project project2 = new Project("Family Birthday");
        project2.addTask( new Task("Secretly find out gifts", getFutureRandomDate(), true ));
        project2.addTask( new Task("Buy gifts", getFutureRandomDate(), false ));
        project2.addTask( new Task("Buy birthday card", getFutureRandomDate(), true ));
        project2.addTask( new Task("Organize the party", getFutureRandomDate(), false ));

        service.saveProject(project2);

        Project project3 = new Project("Business Report");
        Date x = getFutureRandomDate();
        Date y = getFutureRandomDate(x);
        Date z = getFutureRandomDate(y);
        project3.addTask( new Task("Set up a meeting", x, true ));
        project3.addTask( new Task("Hold the meeting", y, false ));
        project3.addTask( new Task("Write the report", z, false ));
        project3.addTask( new Task("Furnish report to the boss", getFutureRandomDate(z), false ));

        service.saveProject(project3);
    }

}
