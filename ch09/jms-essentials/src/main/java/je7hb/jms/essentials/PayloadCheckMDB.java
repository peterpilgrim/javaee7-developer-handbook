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

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

/**
 * The type PayloadCheckMDB
 *
 * @author Peter Pilgrim (peter)
 */

@MessageDriven(
    mappedName = "jms/demoQueue",
    activationConfig = {
//        @ActivationConfigProperty(
//            propertyName="connectionFactoryLookup",
//            propertyValue = "jms/demoConnectionFactory"
//        ),
//        @ActivationConfigProperty(
//            propertyName="destinationLookup",
//            propertyValue = "jms/demoQueue"
//        ),
        @ActivationConfigProperty(
            propertyName="acknowledgeMode",
            propertyValue = "Auto-acknowledge"
        ),
        @ActivationConfigProperty(
            propertyName="messageSelector",
            propertyValue = "SpecialCode = 'Barbados'"
        ),
    }
)
public class PayloadCheckMDB implements MessageListener {

    @EJB
    private PayloadCheckReceiverBean receiver;

    public void onMessage(Message message) {
        try {
            TextMessage textMsg = (TextMessage)message;
            String text = textMsg.getText();
            System.out.printf("%s.onMessage(%s) Thread: %s\n",
                getClass().getSimpleName(), text, Thread.currentThread());
            receiver.addMessage(text);
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
