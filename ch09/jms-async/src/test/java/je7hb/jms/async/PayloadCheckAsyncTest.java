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

package je7hb.jms.async;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * A unit test PayloadCheckTest to verify the operation of PayloadCheckTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class PayloadCheckAsyncTest {

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        final JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(PayloadCheckAsync.class)
//                .addAsResource(
//                        "test-persistence.xml",
//                        "META-INF/persistence.xml")
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));

        System.out.println(jar.toString(true));
        return jar;
    }

    private List<String> messages = new ArrayList<>();

    private CompletionListener completionListener = new CompletionListener() {
        @Override
        public void onCompletion(Message msg) {
            TextMessage textMsg = (TextMessage)msg;
            try {
                System.out.printf("%s.onCompletion(%s) Thread: %s\n",
                        getClass().getSimpleName(), textMsg.getText(), Thread.currentThread());
            } catch (JMSException e) {
                e.printStackTrace(System.err);
            }
        }

        @Override
        public void onException(Message msg, Exception ex) {
            ex.printStackTrace(System.err);
        }
    };

    @EJB
    PayloadCheckAsync service;

    @Test
    @RunAsClient
    public void shouldFireMessageAsynchronously() throws Exception {

        System.out.print("Waiting ...");
        Thread.sleep(1000);
        System.out.println("Ok");

        final Hashtable properties = new Hashtable(2);
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put(Context.PROVIDER_URL, "mq://localhost:7676");
        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        final InitialContext jndiContext = new InitialContext(properties);

        final ConnectionFactory connectionFactory =
            (ConnectionFactory)jndiContext.lookup("jms/demoConnectionFactory");

        final Queue queue = (Queue)jndiContext.lookup("jms/demoQueue");

        final JMSContext context = connectionFactory.createContext(
                Session.AUTO_ACKNOWLEDGE );
        final JMSProducer producer = context.createProducer();

        messages.clear();

        producer.setAsync(completionListener);

        producer.send(queue, "hello");
        producer.send(queue, "world");
        producer.send(queue, "asynchronously");

        System.out.println(".... waiting ...");

        Thread.sleep(2000);

        System.out.println("Done");
    }

    public void shouldFireMessage() {
        service.sendPayloadMessage("hello");
        service.sendPayloadMessage("world");
        assertEquals("hello", service.getMessages().get(0));
        assertEquals("world", service.getMessages().get(1));
    }
}
