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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
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
import static org.junit.Assert.assertNotNull;

/**
 * A unit test ContactConsumer1ServiceBeanTest to verify the operation of ContactConsumer1ServiceBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ContactConsumer2ServiceBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Contact2PK.class)
            .addClasses(ContactConsumer2.class)
            .addClasses(ContactConsumer2ServiceBean.class)
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
//            .addAsResource("jbossas-ds.xml", "META-INF/jbossas-ds.xml")
            .addAsManifestResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("beans.xml"));
        return jar;
    }

    @EJB
    ContactConsumer2ServiceBean service;

    @PersistenceContext
    EntityManager em;

    @Test
    public void shouldPersistConsumers() throws Exception{
        System.out.printf("service=%s\n", service);

        assertEquals( 0, service.getContactConsumers().size());

        service.save(new ContactConsumer2(100, "Annabel",  "Smith"));
        service.save(new ContactConsumer2(200, "Benjamin", "Cranfield"));
        service.save(new ContactConsumer2(300, "Charlie",  "Rogers"));
        service.save(new ContactConsumer2(400, "Debbie",   "Eisen"));

        List<ContactConsumer2> list = service.getContactConsumers();
        assertEquals( 4, service.getContactConsumers().size());

        for (ContactConsumer2 consumer: list) {
            System.out.printf("consumer=%s\n", consumer );
            List<ContactConsumer2> queryList1 = service.findContactConsumers(consumer.getContactId());
            System.out.printf("queryList1=%s\n", queryList1 );
            assertNotNull(queryList1);
            assertEquals(1, queryList1.size());
            assertEquals(consumer, queryList1.get(0));

            ContactConsumer2 consumer2 =
                em.find( ContactConsumer2.class,
                    new Contact2PK(
                            consumer.getContactId(),
                            consumer.getFirstname(),
                            consumer.getLastname() ));

            System.out.printf("consumer2=%s\n", consumer2 );
            assertNotNull(consumer2);
            assertEquals(consumer, consumer2);
        }

        for (ContactConsumer2 consumer: list) {
            service.delete(consumer);
        }

        assertEquals( 0, service.getContactConsumers().size());
    }
}
