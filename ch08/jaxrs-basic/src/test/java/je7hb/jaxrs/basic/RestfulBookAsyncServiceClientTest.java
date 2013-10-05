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
import java.io.File;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A unit test RestfulBookServiceClientTest to verify the operation of RestfulBookServiceClientTest
 *
 * @author Peter Pilgrim
 */
public class RestfulBookAsyncServiceClientTest {

    private static SimpleEmbeddedRunner runner;
    private static WebArchive webArchive;

    @BeforeClass
    public static void assembleDeployAndStartServer() throws Exception {
        webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(RestfulBookAsyncService.class,
                        AddExtraUserAgentFilter.class  )
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
//                .addAsResource(
//                    new File("src/main/resources/META-INF/services/javax.validation.spi.ValidationProvider"),
//                    "META-INF/services/javax.validation.spi.ValidationProvider" )
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));

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
    public void shouldRetrieveBookList() throws Exception {
        WebTarget target = ClientBuilder.newClient()
                .register(new DebugClientLoggingFilter())
                .target("http://localhost:8080/mywebapp/rest/async/books");
        Future<Response> future = target.request().async().get();
        Thread.sleep(6000);
        Response response = future.get( 3, TimeUnit.SECONDS );
        System.out.printf("**** response=%s\n", response);
        assertNotNull(response);

        String text = response.readEntity( String.class );
        System.out.printf("**** text=%s\n", text);
        String arr[] = text.split("\n");
        assertEquals("Don Quixote", arr[0] );
        assertEquals("Robinson Crusoe", arr[1] );
        assertEquals("Gulliver's Travels", arr[2] );
        assertEquals("Frankenstein", arr[3] );
        assertEquals("Jane Eyre", arr[4] );
    }

}
