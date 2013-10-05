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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A unit test JUGMemberServiceBeanTest to verify the operation of JUGMemberServiceBean
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class JUGMemberServiceBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackage(JUGMemberServiceBean.class.getPackage())
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
            .addAsManifestResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB
    JUGMemberServiceBean service;

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    @Before
    public void cleanDatabase() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery( "delete from "+JUGMember.class.getSimpleName()).executeUpdate();
        utx.commit();

        service.save( new JUGMember("Janice","Hendricks","Java", 10 ));
        service.save( new JUGMember("John","Frayton","Scala", 2 ));
        service.save( new JUGMember("Keith","Appleton","Java", 5 ));
        service.save( new JUGMember("Linda","Ramerkon","Java", 3 ));
        service.save( new JUGMember("Oscar","Henry","Groovy", 5 ));
        service.save( new JUGMember("Thomas","Addington","Scala", 1 ));
    }

    @Test
    public void shouldFindAll() {
        List<JUGMember> list = service.findAllMembers();
        System.out.printf("list=%s\n", list );
        assertNotNull(list);
        assertEquals( 6, list.size() );
    }


    @Test
    public void shouldFindByLanguage1() {
        List<JUGMember> list = service.findByLanguage("Scala");
        assertNotNull(list);
        assertEquals( 2, list.size() );
    }

    @Test
    public void shouldFindByLanguage2() {
        List<JUGMember> list = service.findByLanguage("Groovy");
        assertNotNull(list);
        assertEquals( 1, list.size() );
    }

    @Test
    public void shouldFindByFirstAndLastName() {
        List<JUGMember> list = service.findByFirstAndLastName("Oscar","Henry");
        assertNotNull(list);
        assertEquals( 1, list.size() );
    }

    @Test
    public void shouldFindByExperience() {
        List<JUGMember> list = service.findByExperience(5);
        assertNotNull(list);
        assertEquals( 2, list.size() );
    }

    @Test
    public void shouldNotFindByUnknownLanguage() {
        List<JUGMember> list = service.findByLanguage("Pigeon");
        assertNotNull( list);
        assertEquals( 0, list.size() );
    }

    @Test
    public void shouldNotFindByUnknownMember() {
        List<JUGMember> list = service.findByFirstAndLastName("Doctor","Who");
        assertNotNull( list);
        assertEquals( 0, list.size() );
    }

    @Test
    public void shouldNotFindByExperience() {
        List<JUGMember> list = service.findByExperience(77);
        assertNotNull(list);
        assertEquals( 0, list.size() );
    }

}
