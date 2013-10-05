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

/**
 * A unit test FacilitatorBeanTest to verify the operation of FacilitatorBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class FacilitatorBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(ConductorMasterRemote.class)
                .addClasses(ConductorMasterLocal.class)
                .addClasses(ConductorMasterBean.class)
                .addClasses(FacilitatorRemote.class)
                .addClasses(FacilitatorBean.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB
    FacilitatorRemote service;

    @Test
    public void shouldInjectFacilitatorSingleton() {
        assertNotNull(service);

        System.err.println("**** COMMENTED OUT CODE FIXME: service.getConductorMaster() ****");
//        assertNotNull(service.getConductorMaster());
    }
}
