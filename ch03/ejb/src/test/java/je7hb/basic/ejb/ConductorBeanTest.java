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

package je7hb.basic.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

/**
 * Verifies the operation of the ConductorBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ConductorBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Conductor.class,
                ConductorLocal.class, ConductorBean.class)
            .addAsManifestResource(
                EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB Conductor conductor1;
    @EJB Conductor conductor2;
    @EJB Conductor conductor3;

    @Test
    public void shouldInjectSingletonEJB() {
        System.out.printf("conductor1 = %s\n", conductor1 );
        conductor1.orchestrate("conductor1");
        System.out.printf("conductor2 = %s\n", conductor2 );
        conductor1.orchestrate("conductor2");
        System.out.printf("conductor3 = %s\n", conductor3 );
        conductor1.orchestrate("conductor3");
        assertEquals( conductor2, conductor1 );
        assertEquals( conductor2, conductor3 );
        assertEquals( conductor1, conductor3 );
    }
}
