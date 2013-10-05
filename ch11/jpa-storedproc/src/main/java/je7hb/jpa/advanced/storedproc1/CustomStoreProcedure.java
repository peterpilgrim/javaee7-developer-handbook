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

import java.sql.*;

/**
 * The type CustomStoreProcedure for the Derby (JavaDB) database
 * Unfortunately there is a serious bug that prevents JPA 2.1 with the embedded Derby (JavaDB) and
 * embedded GlassFish server.
 *
 * @author Peter Pilgrim (peter)
 */
public class CustomStoreProcedure {

    public static void selectRowsFromTaxAndRegion(int p1, int p2, ResultSet[] data1,
                                                  ResultSet[] data2)
        throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement ps1 = conn.prepareStatement("select * from TAX_CODE where TAX_CODE_ID = ?");
        ps1.setInt(1, p1);
        data1[0] = ps1.executeQuery();

        PreparedStatement ps2 = conn.prepareStatement("select * from REGION where REGION_ID >= ?");
        ps2.setInt(1, p2);
        data2[0] = ps2.executeQuery();

        conn.close();
    }


    public static void selectTaxCode(int p1, ResultSet[] data1 )
        throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement ps1 = conn.prepareStatement("select TAX_CODE_ID, NAME from TAX_CODE where TAX_CODE_ID = ?");
        ps1.setInt(1, p1);
        data1[0] = ps1.executeQuery();

        conn.close();
    }
}
