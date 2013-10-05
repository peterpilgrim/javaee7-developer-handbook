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

package je7hb.basic.singleton;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A unit test ConductorMasterBeanTest to verify the operation of ConductorMasterBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ConductorMasterBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(ConductorMasterRemote.class)
                .addClasses(ConductorMasterLocal.class)
                .addClasses(ConductorMasterBean.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB
    ConductorMasterRemote service;

    @Test
    public void shouldInjectConductorMaster() {
        System.out.printf("service = %s", service );
        assertNotNull(service);
        String hostname = service.getHostname();
        System.out.printf("hostname = %s\n", hostname );
        assertNotNull(hostname);
        int port = service.getPort();
        System.out.printf("port = %d\n", port );
        assertTrue(port > 1023);

        String value = service.getProperty("java.version");
        System.out.printf("value = %s\n",value );
        assertNotNull(value);
    }
}
