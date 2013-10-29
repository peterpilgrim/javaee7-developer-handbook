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

/**
 * The type ChatUser
 *
 * @author Peter Pilgrim
 */
public class ChatUser {
    private final Session session;
    private final String username;

    public ChatUser(Session session, String username) {
        this.session = session;
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatUser)) return false;

        ChatUser chatUser = (ChatUser) o;

        if (session != null ? !session.equals(chatUser.session) : chatUser.session != null) return false;
        if (username != null ? !username.equals(chatUser.username) : chatUser.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = session != null ? session.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "session=" + session +
                ", username='" + username + '\'' +
                '}';
    }
}
