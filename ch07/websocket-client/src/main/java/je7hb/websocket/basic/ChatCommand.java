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

import java.util.*;

/**
 * The type ChatCommand
 *
 * @author Peter Pilgrim
 */
public enum ChatCommand {
    LOGIN("Login"), LOGOUT("Logout"), SEND("Send"),
    RECEIVE("Receive"), UPDATE("Update");

    private String text;

    ChatCommand( String text) { this.text = text; }

    public static ChatCommand convert( String str) {
        if ( str != null ) {
            for ( ChatCommand item: values() ) {
                if (item.text.equalsIgnoreCase(str)) {
                    return item;
                }
            }
        }
        return null;
    }

    public String asText() { return text; }
}
