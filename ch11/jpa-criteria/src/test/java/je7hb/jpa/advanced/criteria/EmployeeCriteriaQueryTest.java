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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.UserTransaction;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Verifies the operation of the EmployeeCriteriaQueryTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class EmployeeCriteriaQueryTest extends AbstractEmployeeCriteriaTest {

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
    public void shouldExecuteCriteriaQuery() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c =
            builder.createQuery(Employee.class);
        Root<Employee> p = c.from(Employee.class);
        Predicate condition = builder.ge(
            p.get(Employee_.salary), new BigDecimal("50000"));
        c.where(condition);
        TypedQuery<Employee> q = em.createQuery(c);
        List<Employee> result = q.getResultList();
        System.out.printf("**** result=%s\n", result);
        assertEquals( NUM_DIRECTORS, result.size());
    }

    public void advancedSearch(
        String firstName, String lastName )
    {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c =
                builder.createQuery(Employee.class);
        Root<Employee> p = c.from(Employee.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
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
        System.out.printf("**** result=%s\n", result);
        assertEquals( NUM_DIRECTORS, result.size());
    }
}
