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

import je7hb.common.webcontainer.embedded.glassfish.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.*;
import org.junit.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;

import static org.junit.Assert.*;

/**
 * A unit test RestfulBookServiceClientTest to verify the operation of RestfulBookServiceClientTest
 *
 * @author Peter Pilgrim
 */
public class RestfulBookServiceClientTest {

    private static SimpleEmbeddedRunner runner;
    private static WebArchive webArchive;

    @BeforeClass
    public static void assembleDeployAndStartServer() throws Exception {
        webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(RestfulBookService.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE, "beans.xml");

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
    public void shouldRetrieveBookList() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/mywebapp/rest/books");
        Response response = target.request().get();
        System.out.printf("**** response=%s\n", response);
        assertNotNull(response);
        String text = response.readEntity( String.class );
        System.out.printf("**** text=%s\n", text);
        String arr[] = text.split("\n");
        assertEquals("Sherlock Holmes and the Hounds of the Baskerville", arr[0] );
        assertEquals("Da Vinci Code", arr[1] );
        assertEquals("Great Expectations", arr[2]);
        assertEquals( "Treasure Island", arr[3] );
    }
}
