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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import static je7hb.websocket.basic.ChatUtils.*;

/**
 * The type ChatServerEndPoint
 *
 * @author Peter Pilgrim
 */
// @Stateless
@ServerEndpoint("/chat")
public class ChatServerEndPoint {

    @Inject
    @ApplicationScoped
    private ChatRoom  chatRoom;


    @PostConstruct
    public void acquire() {
        System.out.printf("%s.acquire() called in thread: [%s]\n",
                getClass().getSimpleName(), Thread.currentThread().getName());
        System.out.printf("  chatRoom = %s\n", chatRoom );
    }

    @PreDestroy
    public void release() {
        System.out.printf("%s.release() called\n", getClass().getSimpleName());
    }

    @OnOpen
    public void open(Session session)  {
        System.out.printf("%s.open() called session=%s\n", getClass().getSimpleName(), session );
        System.out.printf("  chatRoom = %s\n", chatRoom );
    }

    @OnClose
    public void close(Session session) {
        System.out.printf("%s.close() called session=%s\n", getClass().getSimpleName(), session);
    }

    @OnMessage
    public void receiveMessage( String message, Session session ) {
        System.out.printf("%s.receiveMessage() called with message=`%s', session %s, thread [%s]\n",
                getClass().getSimpleName(), message, session, Thread.currentThread().getName() );

        String tokens[] = message.split(DELIMITER);
        String command  = tokens[0];
        String username = ( tokens.length > 1 ? tokens[1] : "" );
        String text     = ( tokens.length > 2 ? tokens[2] : "" );

        ChatUser user = new ChatUser(session,username);
        if ( LOGIN_REQUEST.equals(command))  {
            chatRoom.addChatUser(user);
        }
        else if ( LOGOUT_REQUEST.equals(command))  {
            chatRoom.removeChatUser(user);
        }
        else if ( SEND_MSG_REQUEST.equals(command))  {
            chatRoom.broadcastMessage(username,text);
        }
        else {
            encodeErrorReply(session,username,
                String.format("unknown command: %s", command));
        }
    }
}
