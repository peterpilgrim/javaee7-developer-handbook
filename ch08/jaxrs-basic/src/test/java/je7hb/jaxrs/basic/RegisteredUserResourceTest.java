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

import static org.junit.Assert.*;

import je7hb.common.webcontainer.embedded.glassfish.SimpleEmbeddedRunner;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static je7hb.jaxrs.basic.WebMethodUtils.*;

/**
 * A unit test RegisteredUserResourceTest to verify the operation of RegisteredUserResourceTest
 *
 * @author Peter Pilgrim
 */
public class RegisteredUserResourceTest {

    public static final String ROOT_RES_PATH =
            "http://localhost:8080/mywebapp/rest/users";
    public static final String USER_RES_PATH =
            ROOT_RES_PATH+"/pilgrimp";
    private static SimpleEmbeddedRunner runner;

    @BeforeClass
    public static void beforeAllTests() throws Exception {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "testusers.war")
            .addClasses(RegisteredUserResource.class, User.class,
                UserRegistry.class )
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
            .addAsWebInfResource(
               EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));

        File warFile = new File(webArchive.getName());
        new ZipExporterImpl(webArchive).exportTo(warFile, true);
        runner = SimpleEmbeddedRunner.launchDeployWarFile(
            warFile, "mywebapp", 8080);
    }

    @AfterClass
    public static void afterAllTests() throws Exception {
        runner.stop();
    }


    @Test
    public void shouldAddOneUser() throws Exception {
        Map<String,String> params = new HashMap<String,String>() {
            {
                put("firstName",    "Peter");
                put("lastName",     "Pilgrim");
                put("secretCode",   "19802014");
            }
        };

        List<String> output = makePostRequest(  new URL(USER_RES_PATH), params);
        assertTrue(output.isEmpty());
        List<String> lines = makeGetRequest( new URL(USER_RES_PATH) );
        assertFalse(lines.isEmpty());
        assertEquals("pilgrimp,Peter,Pilgrim,19802014", lines.get(0));
    }

    @Test
    public void shouldAmendOneUser() throws Exception {
        Map<String,String> params1 = new HashMap<String,String>() {
            {
                put("firstName",    "Peter");
                put("lastName",     "Pilgrim");
                put("secretCode",   "19802014");
            }
        };

        makePostRequest( new URL(USER_RES_PATH), params1);

        Map<String,String> params2 = new HashMap<String,String>() {
            {
                put("firstName",    "Pierre");
                put("lastName",     "Pilgrim");
                put("secretCode",   "87654321");
            }
        };

        List<String> output = makePutRequest( new URL(USER_RES_PATH), params2);
        assertTrue(output.isEmpty());
        List<String> lines = makeGetRequest( new URL(USER_RES_PATH) );
        assertFalse(lines.isEmpty());
        assertEquals("pilgrimp,Pierre,Pilgrim,87654321", lines.get(0));
    }

    @Test
    public void shouldDeleteOneUser() throws Exception {
        Map<String,String> params = new HashMap<String,String>() {
            {
                put("firstName",    "Peter");
                put("lastName",     "Pilgrim");
                put("secretCode",   "19802014");
            }
        };
        makePostRequest( new URL(USER_RES_PATH), params);

        List<String> output = makeDeleteRequest(new URL(USER_RES_PATH));
        assertTrue(output.isEmpty());
        List<String> lines = makeGetRequest( new URL(USER_RES_PATH) );
        assertTrue(lines.isEmpty());
    }

    @Test
    public void shouldAddTwoUsers() throws Exception {
        Map<String,String> params1 = new HashMap<String,String>() {
            {
                put("firstName",    "Sally");
                put("lastName",     "Jones");
                put("secretCode",   "33901637");
            }
        };

        makePostRequest( new URL(ROOT_RES_PATH+"/joness"), params1);

        Map<String,String> params2 = new HashMap<String,String>() {
            {
                put("firstName",    "Zara");
                put("lastName",     "Kelly");
                put("secretCode",   "33905674");
            }
        };

        makePostRequest( new URL(ROOT_RES_PATH+"/kellyz"), params2);

        List<String> lines = makeGetRequest( new URL(ROOT_RES_PATH) );
        assertFalse(lines.isEmpty());
        assertEquals("joness,Sally,Jones,33901637", lines.get(0));
        assertEquals("kellyz,Zara,Kelly,33905674", lines.get(1));
    }

    @Test
    public void shouldNotPutUnknownUser() throws Exception {
        Map<String,String> params1 = new HashMap<String,String>() {
            {
                put("firstName",    "Frank");
                put("lastName",     "Butcher");
                put("secretCode",   "123123");
            }
        };

        List<String> lines = makeGetRequest( new URL(ROOT_RES_PATH+"/butcherf") );
        System.out.printf("lines=%s  lines.isEmpty=%s\n", lines, lines.isEmpty());
        assertTrue(lines.isEmpty());
    }

}
