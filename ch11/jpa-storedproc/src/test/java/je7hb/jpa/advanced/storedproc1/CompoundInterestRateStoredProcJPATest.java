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

package je7hb.jpa.advanced.storedproc1;

import je7hb.jpa.Utils;
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
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A unit test CustomStoredProcedureJDBCTest to verify the operation of CustomStoredProcedureJDBCTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
// @CreateSchema({"scripts/create-schema.sql"})
public class CompoundInterestRateStoredProcJPATest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Utils.class)
            .addAsResource(
                new File("src/test/resources-glassfish-managed/scripts/create-schema.sql"),
                "/scripts/create-schema.sql")
            .addAsResource(
                "test-persistence.xml",
                "META-INF/persistence.xml")
            .addAsManifestResource(
                EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"));
        System.out.printf(jar.toString(true));
        return jar;
    }

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    @Resource(name="jdbc/arquillian")
    DataSource dataSource;

    boolean initialised = false;

    @Before
    public void createTablesAndCustomStoredProcedure() throws Exception {
        if ( !initialised) {
            InputStream is = this.getClass().getResourceAsStream("/scripts/create-schema.sql");
            utx.begin();
            Utils.executeScript(em, is, ";", true);
            utx.commit();
            initialised = true;
            em.clear();
        }
    }

    @Test
    public void shouldInvokeStoredProcedureWithOutbound() throws Exception {
        StoredProcedureQuery query =
            em.createStoredProcedureQuery("COMPOUND_INTEREST_SP")
            .registerStoredProcedureParameter(
                "P", Float.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                "r", Float.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                "n", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                "t", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(
                "A", Float.class, ParameterMode.OUT)
            .setParameter("P", new Float(1500))
            .setParameter("r", new Float(0.043))
            .setParameter("n", new Integer(4))
            .setParameter("t", new Integer(6));
        // P = 1500, r = 0.043 (4.3%), n = 4, and t = 6:
        boolean status = query.execute();
        System.out.printf("****************** status=%s\n", status);
        assertFalse( query.hasMoreResults());
        Double A = ( Double)query.getOutputParameterValue("A");
        System.out.printf("The output A was %12.7f\n", A );
        assertEquals( 1938.84, A, 0.005 );
    }
}
