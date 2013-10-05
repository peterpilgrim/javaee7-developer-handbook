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

package je7hb.websocket.basic;

import javax.websocket.*;

/**
 * The type ClientPriceReaderEndpoint
 *
 * @author Peter Pilgrim
 */
@ClientEndpoint
public class ClientPriceReaderEndpoint {
    private RemoteEndpoint.Basic remote;

    @OnOpen
    public void openRemoteConnection( Session session) {
        remote = session.getBasicRemote();
        System.out.printf("%s.openRemoteConnection() session = [%s], ",
            getClass().getSimpleName(), session);
    }

    @OnMessage
    public void messageReceived( Session session, String text ) {
        System.out.printf(">>>> RECEIVED text : %s\n", text);
    }

    @OnClose
    public void closeRemote(CloseReason reason, Session session) {
        System.out.printf("%s.closeRemote() session = [%s], reason=%s",
                getClass().getSimpleName(), session, reason);
    }
}
