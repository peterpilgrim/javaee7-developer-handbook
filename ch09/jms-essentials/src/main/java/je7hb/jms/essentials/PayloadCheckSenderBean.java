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

package je7hb.jms.essentials;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import java.util.ArrayList;
import java.util.List;

/**
 * The type PayloadCheck
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class PayloadCheckSenderBean {

    @Inject
    @JMSConnectionFactory("jms/demoConnectionFactory")
    JMSContext context;

    @Resource(mappedName = "jms/demoQueue")
    private Queue inboundQueue;

    List<String> messages = new ArrayList<>();

    public void sendPayloadMessage( String payload ) {
        sendPayloadMessage(payload,null);
    }

    public void sendPayloadMessage( String payload, String specialCode ) {
        System.out.printf("%s.sendPayloadMessage(%s,%s) Thread: %s\n",
                getClass().getSimpleName(), payload, specialCode, Thread.currentThread());
        JMSProducer producer = context.createProducer();
        if (specialCode != null ) {
            producer.setProperty("SpecialCode", specialCode);
        }
        producer.send(inboundQueue, payload);
        messages.add(payload);
    }
    public List<String> getMessages() {
        return messages;
    }
}
