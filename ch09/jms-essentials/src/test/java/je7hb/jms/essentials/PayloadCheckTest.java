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

package je7hb.jms.essentials;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.List;

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
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(PayloadCheck.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));

        System.out.println(jar.toString(true));
        return jar;
    }

    @EJB
    PayloadCheck service;

    @Test
    public void shouldFireMessage() throws InterruptedException {
        service.sendPayloadMessage("hello");
        service.sendPayloadMessage("world");
        System.out.println("***** Deliberately waiting for a moment or two .... Zzzz");
        Thread.sleep(1560);
        final List<String> messages = service.getMessages();
        assertEquals("hello", messages.get(0));
        assertEquals("world", messages.get(1));
        for ( String msg: service.getMessages()) {
            System.out.printf("Message received was [%s]\n", msg );
        }
    }
}
