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
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Verifies the operation of the RestfulBookServiceTest
 *
 * @author Peter Pilgrim
 */
public class RestfulBookServiceTest {

    @Test
    public void shouldAssembleAndRetrieveBookList() throws Exception {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(RestfulBookService.class, SimpleServlet.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(
                    EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));

        File warFile = new File(webArchive.getName());
        new ZipExporterImpl(webArchive).exportTo(warFile, true);
        SimpleEmbeddedRunner runner =
            SimpleEmbeddedRunner.launchDeployWarFile(
                    warFile, "mywebapp", 8080);
        try {
            URL url = new URL(
                "http://localhost:8080/mywebapp/rest/books");
            InputStream inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
            List<String> lines = new ArrayList<>();
            String text = null;
            int count=0;
            while ( ( text = reader.readLine()) != null ) {
                lines.add(text);
                ++count;
                System.out.printf("**** OUTPUT **** text[%d] = %s\n", count, text );
            }
            assertFalse( lines.isEmpty() );
            assertEquals("Sherlock Holmes and the Hounds of the Baskerville", lines.get(0));
            assertEquals("Da Vinci Code", lines.get(1));
            assertEquals("Great Expectations", lines.get(2));
            assertEquals( "Treasure Island", lines.get(3) );
        }
        finally {
            runner.stop();
        }
    }
}
