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

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * The type ChatCommandDecoder
 *
 * @author Peter Pilgrim
 */
public class ChatCommandDecoder
    implements Decoder.Text<ChatCommand> {
    @Override
    public ChatCommand decode(String s) throws DecodeException {
        ChatCommand value =  ChatCommand.convert(s);
        if ( value == null)
            throw new DecodeException(s, "Cannot decode text");
        return value;
    }

    @Override
    public boolean willDecode(String s) {
        return ChatCommand.convert(s) != null;
    }

    @Override
    public void init(EndpointConfig config) { }

    @Override
    public void destroy() { }
}
