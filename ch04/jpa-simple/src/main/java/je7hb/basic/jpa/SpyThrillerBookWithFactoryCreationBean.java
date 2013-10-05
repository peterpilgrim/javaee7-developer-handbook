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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.*;
import java.util.List;


/**
 * The type SpyThrillerBookBean
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class SpyThrillerBookWithFactoryCreationBean {

    @PersistenceUnit(unitName="testDatabase")
    private EntityManagerFactory factory;

    private EntityManager em;

    @PostConstruct
    public void init() {
        System.out.printf("%s#init()\n", getClass().getSimpleName() );
        em = factory.createEntityManager();
        System.out.printf("em=%s\n", em );
    }

    @PreDestroy
    @Remove
    public void destroy() {
        System.out.printf("%s#destroy() em=%s\n", getClass().getSimpleName(), em );
        em.close();
    }

    public void addBook(SpyThriller movie) throws Exception {
        System.out.printf("%s#addBook() em=%s, movie=%s\n", getClass().getSimpleName(), em, movie );
        if ( !em.isJoinedToTransaction()) em.joinTransaction();
        em.persist(movie);
        em.flush();
    }

    public void deleteBook(SpyThriller movie) throws Exception {
        System.out.printf("%s#deleteBook() em=%s, movie=%s\n", getClass().getSimpleName(), em, movie );
        if ( !em.isJoinedToTransaction()) em.joinTransaction();
        em.remove(movie);
        em.flush();
    }

    public List<SpyThriller> getBooks() throws Exception {
        System.out.printf("%s#deleteBook() em=%s\n", getClass().getSimpleName(), em  );
        if ( !em.isJoinedToTransaction()) em.joinTransaction();
        Query query = em.createQuery("SELECT m from SpyThriller as m");
        return query.getResultList();
    }
}