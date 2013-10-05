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

package je7hb.jpa.advanced.criteria;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Verifies the operation of the EmployeeCriteriaQueryTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class EmployeeCriteriaAdvancedSearchTest extends AbstractEmployeeCriteriaTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(AbstractEmployeeCriteriaTest.class,
                Employee.class, Employee_.class,
                Region.class, TaxCode.class)
            .addAsResource(
                new File("src/test/resources-glassfish-managed/scripts/create-schema.sql"),
                "/scripts/create-schema.sql")
            .addAsResource(
                "test-persistence.xml",
                "META-INF/persistence.xml")
            .addAsManifestResource(
                EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"));
        return jar;
    }

    @Test
    public void shouldExecuteCriteriaQuery() throws Exception {
        utx.begin();
        em.persist(new Employee(9101, "John", "Williams"));
        em.persist(new Employee(9102, "John", "Bradford") );
        em.persist(new Employee(9103, "June", "Bradford") );
        utx.commit();

        List<Employee> res1 = advancedSearch( "John", null );
        System.out.printf("**** res1=%s\n", res1);
        assertEquals(2, res1.size());
        List<Employee> res2 = advancedSearch( null, "Bradford" );
        System.out.printf("**** res2=%s\n", res2);
        assertEquals(2, res2.size());
        List<Employee> res3 = advancedSearch( "June", "Bradford" );
        System.out.printf("**** res3=%s\n", res3);
        assertEquals(1, res3.size());
    }

    public List<Employee> advancedSearch(
        String firstName, String lastName )
    {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c =
                builder.createQuery(Employee.class);
        Root<Employee> p = c.from(Employee.class);
        List<Predicate> predicates = new ArrayList<>();
        if ( firstName != null ) {
            predicates.add(
                builder.like(p.get(Employee_.firstName),
                    firstName));
        }
        if ( lastName != null ) {
            predicates.add(
                builder.like(p.get(Employee_.lastName),
                        lastName));
        }
        c.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Employee> q = em.createQuery(c);
        List<Employee> result = q.getResultList();
        return result;
    }
}
