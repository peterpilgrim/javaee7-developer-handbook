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

package je7hb.intro.xentracker.entity;

import je7hb.intro.xentracker.StringHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * A unit test PersistProjectAndTasksTest to verify the operation of PersistProjectAndTasksTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class PersistProjectAndTasksTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Project.class, Task.class, StringHelper.class)
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

    @Test
    public void shouldPersistProject() throws Exception {
        Project proj1 = new Project("xenonique1");
        utx.begin();
        em.persist(proj1);
        utx.commit();
        System.out.printf("****** proj1=%s\n", proj1);
        assertNotNull( proj1.getId());
    }


    @Test
    public void shouldPersistProjectAndTasks() throws Exception {
        Project proj1 = new Project("xenonique2");
        final int N =3;
        for ( int j=0; j<N; ++j) {
            Date targetDate = null;
            if ( j > 0 ) {
                targetDate = new Date(System.currentTimeMillis() + (long) (Math.random() * 86400 * 5 * 1000 ));
            }
            Task task = new Task("task"+j, targetDate, false );
            proj1.addTask(task);
            task.setProject(proj1);
        }
        utx.begin();
        em.persist(proj1);
        utx.commit();
        System.out.printf("*****  proj1=%s\n", proj1);
        assertNotNull( proj1.getId());
        assertFalse( proj1.getTasks().isEmpty());
        assertThat( proj1.getTasks().size(), is(N));
        for ( Task task: proj1.getTasks()) {
            assertNotNull( task.getId());
            assertThat( task.getProject().getId(), is( proj1.getId()) );
        }
    }
}
