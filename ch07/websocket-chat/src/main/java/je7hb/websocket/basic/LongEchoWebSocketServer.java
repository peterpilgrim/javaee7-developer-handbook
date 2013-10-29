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
import java.io.IOException;

/**
 * The type LongEchoWebSocketServer
 *
 * @author Peter Pilgrim
 */
public class LongEchoWebSocketServer extends Endpoint {
    @Override
    public void onOpen(Session session, EndpointConfiguration config) {
        String query = session.getQueryString();
        System.out.printf("%s#onOpen() session=%s, query=%s\n");
        final RemoteEndpoint.Basic remoteEndpoint = session.getBasicRemote();
        session.addMessageHandler( new MessageHandler.Basic<String>() {
            @Override
            public void onMessage(String message) {
            try {
                System.out.printf("#### Received message: `%s' ####", message);
                remoteEndpoint.sendText(
                    String.format(
                        "Echoing your message: %s\n", message));
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });
    }
}
