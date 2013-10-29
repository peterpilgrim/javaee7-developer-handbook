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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static je7hb.websocket.basic.ChatUtils.SYSTEM_USER;
import static je7hb.websocket.basic.ChatUtils.encodeMessageReply;

/**
 * The type ChatRoom
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class ChatRoom {
    private ConcurrentMap<String,ChatUser> peers
            = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        System.out.printf(">>>> %s.init() called\n",
                getClass().getSimpleName());
    }

    @PreDestroy
    public void destroy() {
        System.out.printf(">>>> %s.destroy() called\n",
                getClass().getSimpleName());
    }

    public void addChatUser( ChatUser user) {
        peers.putIfAbsent(user.getUsername(), user);
        broadcastMessage(SYSTEM_USER,
            String.format("user: %s has joined the chat room.",
            user.getUsername()));

        List<String> peerUsers = new ArrayList<>();
        for ( String peerUsername: peers.keySet()) {
            if ( !peerUsername.equals(user.getUsername())) {
                peerUsers.add(peerUsername);
            }
        }
        StringBuilder membersList = new StringBuilder();
        for ( int j=0; j<peerUsers.size(); ++j) {
            if ( j != 0 ) membersList.append(", ");
            membersList.append(peerUsers.get(j));
        }
        encodeMessageReply(user.getSession(), SYSTEM_USER,
            String.format("The chatroom has members: [%s]",
                membersList.toString()));
    }

    public void removeChatUser( ChatUser user ) {
        peers.remove(user.getUsername()) ;
        broadcastMessage(SYSTEM_USER,
                String.format("user: %s has left the chat room.",
                        user.getUsername()));
    }

    public void broadcastMessage( String targetUsername, String message )
    {
        for ( ChatUser peerUser: peers.values() ) {
            if ( peerUser.getSession().isOpen() ) {
                encodeMessageReply(
                    peerUser.getSession(),
                    targetUsername, message);
            }
        }
    }
}
