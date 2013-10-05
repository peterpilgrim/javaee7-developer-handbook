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

package je7hb.basic3.jpa;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * The type JUGMemberServiceBean
 *
 * @author Peter Pilgrim (peter)
 */
@Stateful
public class JUGMemberServiceBean {

    @PersistenceContext(unitName = "testDatabase", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void save( JUGMember member ) throws Exception {
        entityManager.persist(member);
    }

    public void delete( JUGMember member ) throws Exception {
        entityManager.remove(member);
    }

    public List<JUGMember> findAllMembers() {
        return entityManager.createNamedQuery(
                "JUGMember-findAllMembers")
                .getResultList();
    }

    public List<JUGMember> findByFirstAndLastName(
            String firstname, String lastname) {
        return entityManager.createNamedQuery(
                "JUGMember-findByFirstAndLastName")
                .setParameter("firstname", firstname)
                .setParameter("lastname",lastname)
                .getResultList();
    }

    public List<JUGMember> findByLanguage(String language) {
        return entityManager.createNamedQuery(
                "JUGMember-findByLanguage")
                .setParameter("language", language)
                .getResultList();
    }

    public List<JUGMember> findByExperience(int experience) {
        return entityManager.createNamedQuery(
                "JUGMember-findByExperience")
                .setParameter( 1, experience )
                .getResultList();
    }
}
