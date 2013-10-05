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

package je7hb.basic4.jpa.onetomany;

import je7hb.Utils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.util.*;

import static org.junit.Assert.*;

/**
 * A unit test OneToOneRelationshipTest to verify the operation of OneToOneRelationshipTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class OneToManyRelationshipTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Project.class)
            .addClasses(Task.class)
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
            .addAsManifestResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("beans.xml"));
        return jar;
    }

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    @Before
    public void cleanDatabase() throws Exception {
        utx.begin();
        assertNotNull(em);
        em.joinTransaction();
        em.createQuery("delete from "+Project.class.getSimpleName()).executeUpdate();
        em.createQuery("delete from "+Task.class.getSimpleName()).executeUpdate();
        utx.commit();
    }

    @After
    public void clearEntityManager() {
        Utils.clearEntityManager(em);
    }

    @Test
    public void shouldPersistProjectsWithTasks() throws Exception {
        // Assign
        List<Task> tasks = Arrays.asList(
            new Task(1, 5,  "Begin",  "Tell'em what you are going to tell'em." ),
            new Task(2, 20, "Middle", "Tell'em." ),
            new Task(3, 5,  "End",    "Tell'em what you have told'em." )
        );

        Project proj1 = new Project( "News at 9 O'Clock", tasks );

        // Act
        utx.begin();
        em.persist(proj1);
        utx.commit();

        utx.begin();
        Project proj2 = em.find( Project.class, proj1.getId() );
        utx.commit();

        System.out.printf("proj1 = %s\n", proj1 );
        System.out.printf("proj2 = %s\n", proj1 );

        // Asserts
        assertTrue(proj1.getId() > 0);
        for ( Task task: tasks) {
            assertTrue( task.getId() > 0 );
            assertTrue( proj1.getTasks().contains(task));
        }

        assertEquals( proj1, proj2 );
        for ( Task task: tasks) {
            assertTrue( task.getId() > 0 );
            assertTrue( proj2.getTasks().contains(task));
        }
    }


}
