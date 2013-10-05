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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * A unit test SupportHelpDeskBeanTest to verify the operation of SupportHelpDeskBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class SupportHelpDeskBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(SupportHelpDesk.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB SupportHelpDesk desk;

    @Test
    public void shouldRetrieveDifferentAgents() {
        System.out.printf("Support help desk = %s\n", desk );
        for ( int j=0; j<5; ++j ) {
            String agent  = desk.getNextAgentName();
            System.out.printf("The next agent = %s\n", agent );
            assertNotNull(agent);
        }
    }

    @Test
    public void retrieveUniqueNotNullAgentNames() {
        List<String> names1 = new ArrayList<>();
        Set<String> names2 = new HashSet<>();
        for ( int j=0; j<125; ++j ) {
            String agent  = desk.getNextAgentName();
            assertNotNull(agent);
            names1.add(agent);
            names2.add(agent);
        }
        assertEquals( names1.size(), names2.size() );
        assertTrue( names1.size() > 0 );
    }
}


