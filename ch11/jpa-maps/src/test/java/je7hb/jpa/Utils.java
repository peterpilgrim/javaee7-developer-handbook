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

package je7hb.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * The type Utils
 *
 * @author Peter Pilgrim (peter)
 */
public final class Utils {
    public static <K,V> void assertEqualMaps(Map<K, V> m1, Map<K, V> m2) {
        if ( m1.size() != m2.size() ) {
            fail( String.format(
                "map difference sizes m1.size = %d, m2.size = %d",
                m1.size(), m2.size() ));
        }
        for (K key: m1.keySet()) {
            if ( !m1.get(key).equals(m2.get(key))) {
                fail( String.format(
                    "map key (m1 %s -> %s) is not equal to " +
                    "(m2 %s -> %s )",
                    key, m1.get(key), key, m2.get(key)));
            }
        }
    }

    private static List<String> processScript(String delimiter, boolean fullLineDelimiter, LineNumberReader lnreader) throws IOException {
        String line = null;
        List<String> commands = new ArrayList<>();
        StringBuilder command = null;
        while ( (line = lnreader.readLine()) != null ) {
            if ( command == null ) {
                command = new StringBuilder();
            }
            line = line.trim();
            if ( line.startsWith("--") || line.startsWith("//")) {
                // ignore line
            }
            else {
                if ( fullLineDelimiter && line.equals(delimiter)) {
                    commands.add(command.toString());
                    command = null;
                }
                else if ( !fullLineDelimiter && line.endsWith( delimiter)) {
                    commands.add( " " +line.substring( 0, line.length() - delimiter.length()));
                    command = null;
                }
                else {
                    command.append(" "+line);
                }
            }
        }
        return commands;
    }

    public static void executeScript( DataSource dataSource, InputStream is, String delimiter, boolean fullLineDelimiter )
            throws Exception
    {
        System.out.printf("executeScript(%s, %s)\n", dataSource, is );
        LineNumberReader lnreader = new LineNumberReader( new InputStreamReader(is));
        Connection conx = dataSource.getConnection();
        List<String> commands = processScript(delimiter, fullLineDelimiter, lnreader);

        for ( int j=0; j<commands.size(); ++j ) {
            String sql = commands.get(j).trim();
            if ( sql.length() > 0 ) {
                Statement stmt = conx.createStatement();
                System.out.printf("executing sql -- %s", sql);
                boolean done = stmt.execute(sql);
                System.out.printf(" -  %s\n", (done?"Result set":"No result set"));
                if ( !conx.getAutoCommit()) {
                    conx.commit();
                }
            }
        }
        conx.close();
    }

    public static void executeScript( EntityManager em, InputStream is, String delimiter, boolean fullLineDelimiter )
            throws Exception
    {
        System.out.printf("executeScript(%s, %s)\n", em, is );
        LineNumberReader lnreader = new LineNumberReader( new InputStreamReader(is));
        List<String> commands = processScript(delimiter, fullLineDelimiter, lnreader);

        for ( int j=0; j<commands.size(); ++j ) {
            String sql = commands.get(j).trim();
            if ( sql.length() > 0 ) {
                System.out.printf("executing native sql -- %s", sql);
                Query query = em.createNativeQuery(sql);
                int updateCount = query.executeUpdate();
                System.out.printf(" -  %d\n", updateCount);
            }
        }
    }


    public static void dumpResultSet(ResultSet rs, PrintStream ps) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = 1;
        while (rs.next()) {
            ps.printf("  [%d] ", count);
            for ( int col = 1; col <= rsmd.getColumnCount(); ++col ) {
                if ( col == 1) System.out.print("|");
                ps.printf("%12s|", rs.getObject(col));
            }
            ps.println();
            ++count;
        }
    }
}
