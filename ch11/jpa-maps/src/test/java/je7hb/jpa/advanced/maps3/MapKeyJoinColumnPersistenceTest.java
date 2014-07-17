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

package je7hb.jpa.advanced.maps3;

import je7hb.jpa.Utils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * A unit test MapKeyJoinColumnPersistenceTest to verify the operation of MapKeyJoinColumnPersistenceTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class MapKeyJoinColumnPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Artist.class, EventType.class, LiveEvent.class, Utils.class)
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
            .addAsManifestResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("beans.xml"));
        return jar;
    }

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    @Test
    public void shouldSaveArtistWithAlbum() throws Exception{
        EventType eventType1 = new EventType(808, "WORLD TOUR");

        Artist artist1 = new Artist(1002, "Lady Gaga" );
        LiveEvent event1 = new LiveEvent(97502, "The Monster Ball Tour", eventType1, artist1 );
        artist1.getEvents().put( eventType1, event1 );

        utx.begin();
        em.persist(artist1);
        utx.commit();

        Artist artist2 = em.find( Artist.class, artist1.getId());
        Utils.assertEqualMaps(artist1.getEvents(), artist2.getEvents());
    }
}
