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

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;


/**
 * The type SpyThrillerBookBean
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class SpyThrillerBookBean {

    @PersistenceContext(unitName = "testDatabase", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void addBook(SpyThriller movie) throws Exception {
        entityManager.persist(movie);
    }

    public void deleteBook(SpyThriller movie) throws Exception {
        entityManager.remove(movie);
    }

    public List<SpyThriller> getBooks() throws Exception {
        Query query = entityManager.createQuery("SELECT m from SpyThriller as m");
        return query.getResultList();
    }
}