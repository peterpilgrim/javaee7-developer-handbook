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

import javax.websocket.Session;
import java.io.IOException;

/**
 * The type ChatUtils
 *
 * @author Peter Pilgrim
 */
public class ChatUtils {
    public final static String SYSTEM_USER         = "*SYSTEM*";

    public final static String DELIMITER           = "::::";
    public final static String LOGIN_REQUEST       = "LOGIN";
    public final static String LOGOUT_REQUEST      = "LOGOUT";
    public final static String SEND_MSG_REQUEST    = "SENDMSG";
    public final static String MESSAGE_REPLY       = "MESSAGE_REPLY";
    public final static String ERROR_REPLY         = "ERROR_REPLY";

    private ChatUtils() { }

    public static void encodeMessageReply( Session session, String username, String text ) {
        encodeCommonReply(session, MESSAGE_REPLY, username, text);
    }

    public static void encodeErrorReply( Session session, String username, String text ) {
        encodeCommonReply(session, ERROR_REPLY, username, text);
    }

    public static void encodeCommonReply(Session session, String token, String username, String text) {
        if ( session.isOpen()) {
            try {
                session.getBasicRemote().sendText(
                        token + DELIMITER + username + DELIMITER + text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
