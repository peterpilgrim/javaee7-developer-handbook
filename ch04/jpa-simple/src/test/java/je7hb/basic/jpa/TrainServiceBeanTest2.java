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
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * A unit test SpyThrillerBookBeanTest to verify the operation of SpyThrillerBookBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class TrainServiceBeanTest2 {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackage(TrainServiceBean2.class.getPackage())
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
//            .addAsResource("jbossas-ds.xml", "META-INF/jbossas-ds.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }

    @EJB
    TrainServiceBean2 trainService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void shouldPersistTrainEntities() throws Exception {
        System.out.printf("trainService=%s\n", trainService);
        System.out.printf("em=%s\n", em );

        assertEquals( 0, trainService.getTrains().size());

        trainService.save(new Train2( "London", "Glasgow", 20140423 ));
        trainService.save(new Train2( "Edinburgh", "Glasgow", 20130603 ));
        trainService.save(new Train2( "Manchester", "London", 20130712 ));
        trainService.save(new Train2( "London", "Brighton", 20130808 ));
        trainService.save(new Train2("Cardiff", "London", 20131016));

        List<Train2> list = trainService.getTrains();
        assertEquals( 5, list.size());

        for (Train2 train : list) {
            System.out.printf("train=%s\n", train );
            trainService.delete(train);
        }

        assertEquals( 0, trainService.getTrains().size());
    }
}
