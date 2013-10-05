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

package je7hb.common.webcontainer.embedded.glassfish;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The type EmbeddedRunner
 *
 * @author Peter Pilgrim
 */
public class EmbeddedAsyncReaderRunner extends AbstractEmbeddedRunner {

    private static final String LOREM_IPSUM =
        "Lorem ipsum dolor sit amet, consectetur adipisicing " +
        "elit, sed do eiusmod tempor incididunt ut labore et " +
        "dolore magna aliqua. Ut enim ad minim veniam, quis " +
        "nostrud exercitation ullamco laboris nisi ut " +
        "aliquip ex ea commodo consequat. Duis aute irure " +
        "dolor in reprehenderit in voluptate velit esse " +
        "cillum dolore eu fugiat nulla pariatur. Excepteur " +
        "sint occaecat cupidatat non proident, sunt in " +
        "culpa qui officia deserunt mollit anim id " +
        "est laborum.";

    public EmbeddedAsyncReaderRunner(int port) {
        super(port);
    }

    public static void main(String args[]) throws Exception {
        EmbeddedAsyncReaderRunner runner =
                (EmbeddedAsyncReaderRunner)
                new EmbeddedAsyncReaderRunner(8080).init().start();
        runner.deployWithRename(
            "build/libs/ch06-servlets-basic-1.0.war", "mywebapp");
        Thread.sleep(1000);

        String path = String.format(
                "http://localhost:%d/%s/%s", 8080, "mywebapp", "reader");

        URL url = new URL(path);
        System.out.printf("Client connecting to server on path %s\n", path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setChunkedStreamingMode(2);
        conn.setDoOutput(true);
        conn.connect();
        try (BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(conn.getOutputStream()))) {
            System.out.println("Sending data ...");
            output.write("Beginning Text");
            output.flush();
            System.out.println("Sleeping ...");
            Thread.sleep(3000);
            System.out.println("Sending more data ...");
            StringTokenizer stk = new StringTokenizer(LOREM_IPSUM," \t,.");
            while ( stk.hasMoreTokens()) {
                output.write( stk.nextToken());
                output.flush();
                Thread.sleep(200);
            }
            System.out.println("Finishing client");
            output.write("Ending Text");
            output.flush();
            output.close();
        }
        System.out.println("Check standard console ");
        Thread.sleep(1000);
        System.out.println("Disconnecting and shutdown");
        conn.disconnect();
        runner.stop();

        System.exit(0);
    }
}
