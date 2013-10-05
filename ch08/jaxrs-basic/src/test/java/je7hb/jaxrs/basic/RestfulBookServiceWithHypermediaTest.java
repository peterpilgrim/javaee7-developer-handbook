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

package je7hb.jaxrs.basic;

import je7hb.common.webcontainer.embedded.glassfish.SimpleEmbeddedRunner;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Verifies the operation of the RestfulBookServiceTest
 *
 * @author Peter Pilgrim
 */
public class RestfulBookServiceWithHypermediaTest {

    private static SimpleEmbeddedRunner runner;
    private static WebArchive webArchive;

    @BeforeClass
    public static void assembleDeployAndStartServer() throws Exception {
        webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(RestfulBookServiceWithHypermedia.class,
                    SimpleServlet.class)
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
            .addAsWebInfResource(
                    EmptyAsset.INSTANCE, "beans.xml");

//        System.out.println(webArchive.toString(true));

        File warFile = new File(webArchive.getName());
        new ZipExporterImpl(webArchive).exportTo(warFile, true);
        runner = SimpleEmbeddedRunner.launchDeployWarFile(
                warFile, "mywebapp", 8080);
    }

    @AfterClass
    public static void stopServer() throws Exception {
        runner.stop();
    }


    @Test
    public void shouldRetrieveHyperbookById() throws Exception {
        Thread.sleep(250);
        WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/mywebapp/rest/hyperbooks/101");
        Response response = target.request().get();
        System.out.printf("**** response=%s\n", response);
        for ( String headerName: response.getHeaders().keySet()) {
            System.out.printf(" header[%s] = %s\n",
                headerName, response.getHeaderString(headerName));
        };
        assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldRetrieveHyperbooks() throws Exception {
        Thread.sleep(250);
        WebTarget target = ClientBuilder.newClient()
            .target("http://localhost:8080/mywebapp/rest/hyperbooks");
        Response response = target.request().get();
        System.out.printf("**** response=%s\n", response);
        for ( String headerName: response.getHeaders().keySet()) {
            System.out.printf(" header[%s] = %s\n",
                    headerName, response.getHeaderString(headerName));
        };

        Set<Link> links = response.getLinks();
        assertFalse(links.isEmpty());
        for (Link link: links) {
            System.out.printf(
                "link relation uri=%s, rel=%s \n",
                link.getUri(), link.getRel());
        }

        assertEquals(200, response.getStatus());
        Thread.sleep(250);
    }
}
