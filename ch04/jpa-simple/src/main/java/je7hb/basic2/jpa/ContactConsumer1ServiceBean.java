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

package je7hb.basic2.jpa;

import javax.ejb.Stateful;
import javax.persistence.*;
import java.util.List;

/**
 * The type ContactConsumer1ServiceBean
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class ContactConsumer1ServiceBean {

    @PersistenceContext(unitName = "testDatabase", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void save(ContactConsumer1 consumer) throws Exception {
        entityManager.persist(consumer);
    }

    public void delete(ContactConsumer1 consumer) throws Exception {
        entityManager.remove(consumer);
    }

    public List<ContactConsumer1> getContactConsumers() throws Exception {
        Query query = entityManager.createQuery(
                "SELECT c from "+ContactConsumer1.class.getSimpleName()+" as c");
        return query.getResultList();
    }

    public List<ContactConsumer1> findContactConsumers( long id ) throws Exception {
        Query query = entityManager.createQuery(
                "SELECT c from "+ContactConsumer1.class.getSimpleName()+" as c where c.contactId = :contactId");
        query.setParameter("contactId", id);
        return query.getResultList();
    }
}
