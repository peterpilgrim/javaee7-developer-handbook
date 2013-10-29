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
import javax.jms.Queue;
import java.util.*;

/**
 * The type PayloadCheck
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class PayloadCheck {

    @Inject
    @JMSConnectionFactory("jms/demoConnectionFactory")
    JMSContext context;

    @Resource(mappedName = "jms/demoQueue")
    private Queue inboundQueue;

    List<String> messages = new ArrayList<>();

    public void sendPayloadMessage( String payload ) {
        System.out.printf("%s.sendPayloadMessage(%s) Thread: %s\n",
                getClass().getSimpleName(), payload, Thread.currentThread());
        context.createProducer().send( inboundQueue, payload);
        synchronized(messages) {
            messages.add(payload);
        }
    }

    public List<String> getMessages() {
        synchronized (messages) {
            return messages;
        }
    }
}
