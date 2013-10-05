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

package je7hb.jpa.advanced.maps1;

import je7hb.jpa.Utils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.rmi.CORBA.Util;
import javax.transaction.UserTransaction;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * A unit test MapCollectionPersistenceTest to verify the operation of MapCollectionPersistenceTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class MapCollectionPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Album.class, Artist.class, Utils.class)
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
    public void shouldSaveArtistWithAlbum() throws Exception {
        // Assign
        Artist artist1 = new Artist(1002, "Joni Mitchell");

        Album album = new Album();
        album.setAlbumId(8002);
        album.setArtist(artist1);
        album.setTitle("Blue");
        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.YEAR, 1972);
        cal.set( Calendar.MONTH, Calendar.JUNE);
        cal.set( Calendar.DAY_OF_MONTH, 22 );
        cal.set( Calendar.HOUR, 0 );
        cal.set( Calendar.MINUTE, 0 );
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        album.setDate(cal.getTime());
//        album.setDate( new DateTime(1971, 6, 22, 0, 0, 0).toDate() );

        artist1.getAlbums().put( album.getAlbumId(), album );

        // Act
        utx.begin();
        em.persist(artist1);
        utx.commit();

        // Assert
        Artist artist2 = em.find( Artist.class, 1002);
        assertEquals( artist1.getArtistName(), artist2.getArtistName());
        Utils.assertEqualMaps(artist1.getAlbums(), artist2.getAlbums());
    }

}
