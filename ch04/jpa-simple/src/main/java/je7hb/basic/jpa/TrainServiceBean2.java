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
 * The type TrainServiceBean2
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class TrainServiceBean2 {

    @PersistenceContext(unitName = "testDatabase", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void save(Train2 train) throws Exception {
        entityManager.persist(train);
    }

    public void delete(Train2 train) throws Exception {
        entityManager.remove(train);
    }

    public List<Train2> getTrains() throws Exception {
        Query query = entityManager.createQuery("SELECT t from Train2 as t");
        return query.getResultList();
    }
}