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

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The type PayloadCheckReceiverBean
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
@Singleton
public class PayloadCheckReceiverBean {
    private CopyOnWriteArrayList<String> messages =
            new CopyOnWriteArrayList<>();

    public void addMessage(String text) {
        messages.add(text);
        System.out.printf("%s.addMessage(%s) %d Thread: %s\n",
                getClass().getSimpleName(), text, messages.size(), Thread.currentThread());
    }

    public List<String> getMessages() {
        return messages;
    }
}
