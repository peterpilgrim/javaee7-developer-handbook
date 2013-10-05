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

package je7hb.websocket.basic;

import je7hb.common.webcontainer.embedded.glassfish.AbstractEmbeddedRunner;
import je7hb.common.webcontainer.embedded.glassfish.EmbeddedRunner;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.io.File;
import java.net.URI;
import java.util.Scanner;

/**
 * The type StreamingPriceEmbeddedRunner
 *
 * @author Peter Pilgrim
 */
public class StreamingPriceEmbeddedRunner extends AbstractEmbeddedRunner{
    public StreamingPriceEmbeddedRunner(int port) {
        super(port);
    }

    public static void main(String args[]) throws Exception {
        EmbeddedRunner runner =
            (EmbeddedRunner) new EmbeddedRunner(8080)
                .init().start();

        WebArchive webArchive =
            ShrinkWrap.create(WebArchive.class,"mywebapp.war")
            .addPackage(
                ClientPriceReaderEndpoint.class.getPackage())
            .addAsWebInfResource(
                    new File("src/main/webapp/WEB-INF/beans.xml"),
                    "beans.xml")
            .addAsWebResource(
                    new File("src/main/webapp", "index.jsp"))
            .setWebXML(
                    new File("src/main/webapp/WEB-INF/web.xml"));

        System.out.printf(webArchive.toString(true));
        File file = new File(webArchive.getName());
        webArchive.as(ZipExporter.class).exportTo(file, true);

        runner.deployWithRename( webArchive.getName(), "mywebapp");
        Thread.sleep(1000);
        WebSocketContainer container =
            ContainerProvider.getWebSocketContainer();
        container.connectToServer(ClientPriceReaderEndpoint.class,
            new URI("ws://localhost:8080/mywebapp/streamingPrice"));

        System.out.printf("**** Press the ENTER key to stop the server ****\n");
        Scanner sc = new Scanner(System.in);
        while(!sc.nextLine().equals(""));
        runner.stop();
    }
}
