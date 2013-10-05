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
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A unit test FXSpotTradeServicesTest to verify the operation of FXSpotTradeServicesTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class FXSpotTradeServicesTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addPackage(FXSpotTradeServiceBean.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    @EJB
    FXSpotTradeServiceLocal service;


    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void before() throws Exception {
        clearDatabase();
    }

    @After
    public void after() throws Exception {
    }

    public void clearDatabase() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from "+FXSpotTrade.class.getSimpleName()).executeUpdate();
        utx.commit();
    }


    @Test
    public void shouldPersistSpotRates() throws Exception {
        assertNotNull(service);

//        FXSpotTrade spot1 = new FXSpotTrade();
//        spot1.setCcy1Buy(true);
//        spot1.setCcy1("EUR");
//        spot1.setCcy2("GBP");
//        spot1.setAmount1(new BigDecimal(1000000));
//        spot1.setAmount2(new BigDecimal(1656900));
//        spot1.setSpotRate(new BigDecimal(1.6559));
//        spot1.setValueDate(new Date());
//        spot1.setCounterparty("USA Street");
//        spot1.setPortfolio("LondonWall");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        FXSpotTrade spot1 = FXSpotTradeBuilder.create()
            .ccy1buy(true)
            .ccy1("EUR").amount1(new BigDecimal(1000000))
            .ccy2("GBP").amount2(new BigDecimal(1656900))
            .spotRate(new BigDecimal(1.6559))
            .valueDate(new Date())
            .counterparty("USA Street")
            .portfolio("LondonWall")
            .build();

        service.save(spot1);
        System.out.printf("spot1 = %s\n", spot1);
        assertNotNull(spot1.getId());

        List<FXSpotTrade> trades = service.getBooks();
        assertNotNull(trades);
        assertEquals( 1, trades.size() );
        assertTrue( trades.get(0).semanticallyEquals( spot1 ));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
