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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * The type FXSpotTradeServiceBean
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class FXSpotTradeServiceBean implements FXSpotTradeServiceRemote, FXSpotTradeServiceLocal {

    @PersistenceContext(unitName = "jfxSpotTradeDatabase",
        type = PersistenceContextType.TRANSACTION )
    private EntityManager entityManager;

    @Override
    public void save(FXSpotTrade spot1) {
        entityManager.persist(spot1);
    }

    public List<FXSpotTrade> getBooks() throws Exception {
        Query query = entityManager.createQuery("SELECT t from FXSpotTrade as t");
        return query.getResultList();
    }
}
