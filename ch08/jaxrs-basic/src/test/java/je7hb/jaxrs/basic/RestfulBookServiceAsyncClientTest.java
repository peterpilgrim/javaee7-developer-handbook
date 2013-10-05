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
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * A unit test RestfulBookServiceClientTest to verify the operation of RestfulBookServiceClientTest
 *
 * @author Peter Pilgrim
 */
public class RestfulBookServiceAsyncClientTest {

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
    public void shouldRetrieveBookListAsynchronously() {
        WebTarget target =
            ClientBuilder.newClient()
                .target("http://localhost:8080/mywebapp/rest/books");
        Future<Response> future = target.request().async().get();

        try {
            System.out.printf("**** future=%s\n", future);
            assertNotNull(future);
            Response response = future.get(3, TimeUnit.SECONDS );
            System.out.printf("**** response=%s\n", response);
            String text = response.readEntity( String.class );
            System.out.printf("**** text=%s\n", text);
            String arr[] = text.split("\n");
            assertEquals("Sherlock Holmes and the Hounds of the Baskerville", arr[0] );
            assertEquals("Da Vinci Code", arr[1] );
            assertEquals("Great Expectations", arr[2]);
            assertEquals( "Treasure Island", arr[3] );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static class MyCallback implements InvocationCallback<Response> {

        public CountDownLatch ready = new CountDownLatch(1);
        public volatile String text = "";
        public volatile Throwable failure = null;

        @Override
        public void completed(Response response) {
            text = response.readEntity( String.class );
            System.out.printf("**** MyCallback.completed() text=%s\n", text);
            ready.countDown();
        }

        @Override
        public void failed(Throwable throwable) {
            System.out.printf("**** MyCallback.failed() throwable=%s\n", throwable.getMessage());
            failure = throwable;
            ready.countDown();
        }
    }

    @Test
    public void shouldRetrieveBookListAsyncWithCallback() {
        System.out.println("**** shouldRetrieveBookListAsyncWithCallback()");
        WebTarget target =
                ClientBuilder.newClient()
                    .target("http://localhost:8080/mywebapp/rest/books");

        MyCallback callback = new MyCallback();
        Future<Response> future =
            target.request().async().get(callback);

        try {
            System.out.printf("**** future=%s\n", future);
            assertNotNull(future);
            Response response =
                future.get(1, TimeUnit.SECONDS );
            System.out.printf("**** response=%s\n", response);
            callback.ready.await(2, TimeUnit.SECONDS);
            if ( callback.failure != null )
                callback.failure.printStackTrace(System.err);
            assertNull(callback.failure);
            String arr[] = callback.text.split("\n");
            assertEquals("Sherlock Holmes and the Hounds of the Baskerville", arr[0] );
            assertEquals("Da Vinci Code", arr[1] );
            assertEquals("Great Expectations", arr[2]);
            assertEquals( "Treasure Island", arr[3] );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
