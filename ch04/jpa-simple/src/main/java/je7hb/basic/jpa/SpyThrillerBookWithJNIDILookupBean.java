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

import javax.annotation.Resource;
import javax.ejb.SessionContext;
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
@PersistenceContext(
        unitName = "testDatabase",
        name = "myLookupName",
        type = PersistenceContextType.EXTENDED)
public class SpyThrillerBookWithJNIDILookupBean {

    @Resource private SessionContext ctx;

    public EntityManager getEntityManager() {
        EntityManager em = (EntityManager)ctx.lookup("myLookupName");
        System.out.printf("em=%s\n", em );
        return em;
    }
    public void addBook(SpyThriller movie) throws Exception {
        getEntityManager().persist(movie);
    }

    public void deleteBook(SpyThriller movie) throws Exception {
        getEntityManager().remove(movie);
    }

    public List<SpyThriller> getBooks() throws Exception {
        Query query = getEntityManager().createQuery("SELECT m from SpyThriller as m");
        return query.getResultList();
    }
}
