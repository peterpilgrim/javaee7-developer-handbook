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
import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * A unit test CustomStoredProcedureJDBCTest to verify the operation of CustomStoredProcedureJDBCTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
// @CreateSchema({"scripts/create-schema.sql"})
public class CustomStoredProcedureJDBCTest {

    private static final String TAX_AND_REGION_PROC_NAME = "TAX_AND_REGION_SP";
    private static final String TAX_AND_REGION_SQL = "{ call "+ TAX_AND_REGION_PROC_NAME +"(?, ?)}";

    private static final String READ_TAX_PROC_NAME = "READ_TAX_SP";
    private static final String READ_TAX_SQL = "{ call "+ READ_TAX_PROC_NAME +"(?)}";

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClass(Utils.class)
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

    @Resource(name="jdbc/arquillian",mappedName = "jdbc/arquillian")
    DataSource dataSource;

    static boolean initialised = false;

    @Before
    public void createTablesAndCustomStoredProcedure() throws Exception {
        if ( !initialised) {
            InputStream is = this.getClass().getResourceAsStream("/scripts/create-schema.sql");
            Utils.executeScript(dataSource, is, ";", true );
            initialised = true;
        }
    }

    @Test
    public void shouldCreateAndExecuteUserDefinedProcedure() throws Exception {
        System.out.printf("Execute the store procedure: %s\n", TAX_AND_REGION_PROC_NAME);
        Connection conn = dataSource.getConnection();
        CallableStatement cs = conn.prepareCall(TAX_AND_REGION_SQL);
        cs.setInt(1, 101);
        cs.setInt(2, 83001);

        boolean hasResults = cs.execute();
        while (hasResults) {
            System.out.println("Result Set:");
            ResultSet rs = cs.getResultSet();
            Utils.dumpResultSet(rs, System.out);
            rs.close();
            System.out.println();
            hasResults = cs.getMoreResults();
        }
    }

    @Test
    public void shouldCreateAndExecuteReadTaxProcedure() throws Exception {
        System.out.printf("Execute the store procedure: %s\n", READ_TAX_PROC_NAME);
        Connection conn = dataSource.getConnection();
        CallableStatement cs = conn.prepareCall(READ_TAX_SQL);
        cs.setInt(1, 103);

        boolean hasResults = cs.execute();
        while (hasResults) {
            System.out.println("Result Set:");
            ResultSet rs = cs.getResultSet();
            Utils.dumpResultSet(rs, System.out);
            rs.close();
            System.out.println();
            hasResults = cs.getMoreResults();
        }
    }
}
