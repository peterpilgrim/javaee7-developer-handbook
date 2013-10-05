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

package je7hb.jpa.advanced.entitygraph1;

import je7hb.jpa.Utils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.transaction.UserTransaction;

import static org.junit.Assert.*;
/**
 * A unit test ArtistEntityGraphRetrievalTest to verify the operation of ArtistEntityGraphRetrievalTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ArtistEntityGraphRetrievalTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Artist.class, EventType.class,
                ConcertEvent.class, Contract.class, LiveEvent.class,
                StringHelper.class, Utils.class)
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
    @InSequence(1)
    public void shouldSaveArtistWithAlbum() throws Exception{
        StringBuilder text = new StringBuilder();
        for ( int j=0; j<256; ++j) {
            text.append((char)(65 + Math.random() * 26));
        }
        Contract contract =
            new Contract( 5150, "M and Ms", text.toString());
        EventType eventType = new EventType(808, "WORLD TOUR");
        Artist artist = new Artist(1002, "Lady Gaga" );
        LiveEvent event = new LiveEvent(97502, "The Monster Ball Tour",
            eventType, artist, "Tokyo Dome", 55000 );
        event.getContracts().add(contract);
        contract.setConcertEvent(event);
        artist.getEvents().put( eventType, event );

        utx.begin();
        em.persist(artist);
        utx.commit();

        Artist artist2 = em.find(Artist.class, artist.getId());
        Utils.assertEqualMaps(artist.getEvents(), artist2.getEvents());
    }

    @Test
    @InSequence(2)
    public void shouldLoadArtistWithoutConcerts() throws Exception{
        PersistenceUnitUtil util =
            em.getEntityManagerFactory().getPersistenceUnitUtil();
        EntityGraph artistGraph = em.getEntityGraph("Artist.NoConcerts");
        Artist artist = (Artist) em.createQuery("Select a from Artist a")
            .setHint("javax.persistence.fetchgraph", artistGraph)
            .getResultList()
            .get(0);
        System.out.printf("++++ artist=%s\n", artist);
        System.out.printf(">> loaded  artist.id = %s\n", util.isLoaded(artist, "id"));
        System.out.printf(">> loaded  artist.name = %s\n", util.isLoaded(artist, "name"));
        System.out.printf(">> loaded  artist.events = %s\n", util.isLoaded(artist, "events"));
        assertTrue(util.isLoaded(artist, "id"));
        assertTrue(util.isLoaded(artist, "name"));
        assertFalse(util.isLoaded(artist, "events"));
    }

    @Test
    @InSequence(3)
    public void shouldLoadArtistWithConcerts() throws Exception{
        PersistenceUnitUtil util =
            em.getEntityManagerFactory().getPersistenceUnitUtil();
        EntityGraph artistGraph = em.getEntityGraph("Artist.WithConcerts");
        Artist artist = (Artist)
            em.createQuery("Select a from Artist a")
            .setHint("javax.persistence.fetchgraph", artistGraph)
            .getResultList()
            .get(0);
        System.out.printf("++++ artist=%s\n", artist);
        System.out.printf("artist=%s\n", artist);
        assertTrue(util.isLoaded(artist, "id"));
        assertTrue(util.isLoaded(artist, "name"));
        assertTrue(util.isLoaded(artist, "events"));
    }

    @Test
    @InSequence(4)
    public void shouldLoadArtistWithLiveConcertsAndNoContracts()
        throws Exception {
        PersistenceUnitUtil util =
                em.getEntityManagerFactory().getPersistenceUnitUtil();
        em.clear();
        EntityGraph artistGraph = em.getEntityGraph(
            "Artist.WithConcertsAndNoContracts");
        Artist artist = (Artist)
            em.createQuery("Select a from Artist a")
            .setHint("javax.persistence.fetchgraph", artistGraph)
            .getResultList()
            .get(0);
        System.out.printf("++++ artist=%s\n", artist);
        assertTrue(util.isLoaded(artist, "id"));
        assertTrue(util.isLoaded(artist, "name"));
        assertTrue(util.isLoaded(artist, "events"));
        ConcertEvent event = artist.getEvents().values().iterator().next();
        System.out.printf("++++ concert event=%s\n", event );
        assertTrue(util.isLoaded(event, "id"));
        assertTrue(util.isLoaded(event, "name"));
        assertTrue(util.isLoaded(event, "eventType"));
        assertFalse(util.isLoaded(event, "contracts"));
    }

    @Test
    @InSequence(5)
    public void shouldLoadArtistWithLiveConcertsAndContracts()
        throws Exception{
        PersistenceUnitUtil util =
                em.getEntityManagerFactory().getPersistenceUnitUtil();
        em.clear();
        EntityGraph artistGraph = em.getEntityGraph("Artist.WithConcertsAndContracts");
        Artist artist = (Artist)
            em.createQuery("Select a from Artist a")
            .setHint("javax.persistence.fetchgraph", artistGraph)
            .getResultList()
            .get(0);
        System.out.printf("++++ artist=%s\n", artist);
        assertTrue(util.isLoaded(artist, "id"));
        assertTrue(util.isLoaded(artist, "name"));
        assertTrue(util.isLoaded(artist, "events"));
        ConcertEvent event = artist.getEvents().values().iterator().next();
        System.out.printf("++++ concert event=%s\n", event );
        assertTrue(util.isLoaded(event, "id"));
        assertTrue(util.isLoaded(event, "name"));
        assertTrue(util.isLoaded(event, "eventType"));
        assertTrue(util.isLoaded(event, "contracts"));
    }
}
