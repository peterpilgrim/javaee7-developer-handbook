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

package je7hb.basic.jpa;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A unit test EntityManagerByInjectionTest to verify the entity manager can be injected
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class EntityManagerByJNDITest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClass(SpyThriller.class)
            .addClass(SpyThrillerBookWithJNIDILookupBean.class)
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
            .addAsManifestResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB SpyThrillerBookWithJNIDILookupBean bookService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void shouldLookupEntityManagerByJNDI() throws Exception {
        System.out.printf("bookService=%s\n", bookService);

        assertEquals( 0, bookService.getBooks().size());

        bookService.addBook(new SpyThriller(
                "The Spy Who Came in from the Cold", 1963,
                "John Le Carre" ));
        bookService.addBook(new SpyThriller(
                "Casino Royale", 1953, "Ian Fleming"));
        bookService.addBook(new SpyThriller(
                "The Hunt for Red October ", 1984, "Tom Clancy"));
        bookService.addBook(new SpyThriller(
                "Bravo Two Zero", 1993, "Andy McNab" ));
        bookService.addBook(new SpyThriller(
                "On Her Majesty's Secret Service", 1963,
                "Ian Fleming"));

        List<SpyThriller> list = bookService.getBooks();
        assertEquals( 5, list.size());

        for (SpyThriller movie : list) {
            System.out.printf("movie=%s\n", movie );
            bookService.deleteBook(movie);
        }

        assertEquals( 0, bookService.getBooks().size());
    }
}
