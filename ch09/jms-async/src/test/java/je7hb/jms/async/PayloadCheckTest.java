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

package je7hb.jms.async;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.io.File;

import static org.junit.Assert.*;

/**
 * A unit test PayloadCheckTest to verify the operation of PayloadCheckTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class PayloadCheckTest {

    @Deployment
    public static JavaArchive createDeployment() {
        final JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(PayloadCheck.class)
                .addAsResource(
                        new File("src/test/resources-glassfish-managed/glassfish-resources.xml"),
                        "glassfish-resources.xml")
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));

        System.out.println(jar.toString(true));
        return jar;
    }

    @EJB
    PayloadCheck service;

    @Test
    public void shouldFireMessage() throws Exception {
        service.sendPayloadMessage("hello");
        service.sendPayloadMessage("world");

        System.out.println("Sleeping for a bit ... ZZZZ");
        Thread.sleep(1895);
        assertEquals("hello", service.getMessages().get(0));
        assertEquals("world", service.getMessages().get(1));
    }
}
