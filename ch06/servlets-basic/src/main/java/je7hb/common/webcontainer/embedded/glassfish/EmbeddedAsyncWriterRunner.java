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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * The type EmbeddedRunner
 *
 * @author Peter Pilgrim
 */
public class EmbeddedAsyncWriterRunner extends AbstractEmbeddedRunner {

    public EmbeddedAsyncWriterRunner(int port) {
        super(port);
    }

    public static void main(String args[]) throws Exception {
        EmbeddedAsyncWriterRunner runner =
                (EmbeddedAsyncWriterRunner)
                new EmbeddedAsyncWriterRunner(8080).init().start();
        runner.deployWithRename(
            "build/libs/ch06-servlets-basic-1.0.war", "mywebapp");
        Thread.sleep(1000);

        String path = String.format(
                "http://localhost:%d/%s/%s", 8080, "mywebapp", "writer");

        URL url = new URL(path);
        System.out.printf("Client connecting to server on path %s\n", path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setChunkedStreamingMode(2);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            System.out.println("Client receiving data ...");
            int len = -1;
            char buffer[] = new char[2048];
            while ((len = input.read(buffer)) != -1 ) {
                String data = new String(buffer,0,len).trim();
                System.out.printf("--> client received data: %s\n", data );
            }
            System.out.println("Client finished with receiving data ...");
        }
        System.out.println("Check standard console ");
        Thread.sleep(3000);
        System.out.println("Client disconnecting and shutdown");
        conn.disconnect();
        runner.stop();

        System.exit(0);
    }
}
