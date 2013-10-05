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

package je7hb.basic4.jpa.onetoone;

import je7hb.Utils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import static org.junit.Assert.*;

/**
 * A unit test OneToOneRelationshipTest to verify the operation of OneToOneRelationshipTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class OneToOneRelationshipTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Employee.class)
            .addClasses(Address.class)
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
        em.createQuery("delete from "+Employee.class.getSimpleName()).executeUpdate();
        em.createQuery("delete from "+Address.class.getSimpleName()).executeUpdate();
        utx.commit();
    }

    @After
    public void clearEntityManager() {
        Utils.clearEntityManager(em);
    }

    @Test
    public void shouldPersistEmployeeWithAddress() throws Exception {
        // - "I always set up my tests with the AAA pattern of `Assigns', then `Act', then `Asserts'.

        // Assigns
        Address addr1 = new Address("120", "Bishopsgate Road", "London", "UnitedKingdom");
        Employee emp1 = new Employee(12345, "Helen","Thomas",addr1);

        // Act
        utx.begin();
        em.persist(emp1);
        Employee emp2 = em.find( Employee.class, emp1.getEmployeeId() );
        utx.commit();

//        System.out.printf("addr1 = %s\n", addr1 );
//        System.out.printf("emp1 = %s\n", emp1 );
//        System.out.printf("emp2 = %s\n", emp2 );

        // Asserts
        assertTrue( addr1.getAddressId() > 0);
        assertTrue( emp1.getEmployeeId() > 0 );
        assertNotNull( emp1.getAddress() );
        assertEquals( addr1, emp1.getAddress() );
        assertNotNull(emp2);
        assertEquals( emp1, emp2 );
    }


    @Test
    public void shouldPersistEmployeeWithoutAddress() throws Exception {
        // Assign
        Employee emp1 = new Employee(12345, "Helen","Thomas", null);

        // Act
        utx.begin();
        em.persist(emp1);
        Employee emp2 = em.find( Employee.class, emp1.getEmployeeId() );
        utx.commit();

//        System.out.printf("emp1 = %s\n", emp1 );
//        System.out.printf("emp2 = %s\n", emp2 );

        // Asserts
        assertTrue(emp1.getEmployeeId() > 0);
        assertNull(emp1.getAddress());
        assertNotNull(emp2);
        assertNull(emp2.getAddress());
        assertEquals( emp1, emp2 );
    }
}
