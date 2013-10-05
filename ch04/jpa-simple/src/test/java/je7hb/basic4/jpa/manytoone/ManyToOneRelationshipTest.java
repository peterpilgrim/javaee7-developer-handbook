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

package je7hb.basic4.jpa.manytoone;

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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A unit test OneToOneRelationshipTest to verify the operation of OneToOneRelationshipTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ManyToOneRelationshipTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Employee2.class)
            .addClasses(Clearance.class)
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
        em.createQuery("delete from "+Employee2.class.getSimpleName()).executeUpdate();
        em.createQuery("delete from "+Clearance.class.getSimpleName()).executeUpdate();
        utx.commit();
    }

    @After
    public void clearEntityManager() {
        Utils.clearEntityManager(em);
    }

    @Test
    public void shouldPersistEmployeesWithClearance() throws Exception {
        // Assign
        Clearance sec1 = new Clearance( "Red" );
        List<Employee2> employees = Arrays.asList(
                new Employee2(10010, "Stanley", "Rickmansworth", sec1 ),
                new Employee2(10020, "Harpreet", "DeSilva", sec1 ),
                new Employee2(10030, "Bruno", "Bracorintho", sec1 )
                );


        // Act
        utx.begin();
        for ( Employee2 e: employees) {
            em.persist(e);
        }
        utx.commit();

        utx.begin();
        List<Employee2> employeesReturned =
                em.createQuery("select e from "+Employee2.class.getSimpleName()+" e ")
                .getResultList();
        utx.commit();

        System.out.printf("employees = %s\n", employees );
        System.out.printf("employeesReturned = %s\n", employees );

        // Asserts
        assertTrue(sec1.getSecurityId() > 0);
        for ( Employee2 emp: employees) {
            assertTrue( emp.getEmployeeId() > 0 );
            assertEquals(sec1, emp.getClearance());
        }

        assertNotNull(employeesReturned);
        for ( Employee2 emp: employeesReturned) {
            assertTrue( emp.getEmployeeId() > 0 );
            assertEquals(sec1, emp.getClearance());
        }
    }
}
