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

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type PayloadCheck
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class PayloadCheckAsync {

    @Inject
    @JMSConnectionFactory("jms/demoConnectionFactory")
    JMSContext context;

    @Resource(mappedName = "jms/demoQueue")
    private Queue inboundQueue;

    List<String> messages = new ArrayList<>();

    private CompletionListener completionListener = new CompletionListener() {
        @Override
        public void onCompletion(Message msg) {
            TextMessage textMsg = (TextMessage)msg;
            try {
                System.out.printf("%s.onCompletion(%s) Thread: %s\n",
                        getClass().getSimpleName(), textMsg.getText(), Thread.currentThread());
                messages.add(textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace(System.err);
            }
        }

        @Override
        public void onException(Message msg, Exception ex) {
            ex.printStackTrace(System.err);
        }
    };

    public void sendPayloadMessage( String payload ) {
        System.out.printf("%s.sendPayloadMessage(%s) Thread: %s\n",
                getClass().getSimpleName(), payload, Thread.currentThread());
        JMSProducer producer = context.createProducer();
        producer.setAsync(completionListener);
        context.createProducer().send(inboundQueue, payload);
    }

    public List<String> getMessages() {
        return messages;
    }
}
