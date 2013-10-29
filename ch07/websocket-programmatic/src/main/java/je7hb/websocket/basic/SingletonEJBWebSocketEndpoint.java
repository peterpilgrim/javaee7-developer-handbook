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

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;

/**
 * The type SingletonEJBWebSocketEndpoint
 *
 * @author Peter Pilgrim
 */
@ServerEndpoint("/singleton")
public class SingletonEJBWebSocketEndpoint {

    @Inject
    private SampleSingleton sampleSingleton;

    @OnOpen
    public void open(Session session)  {
        System.out.printf("%s.open() called session=%s\n", getClass().getSimpleName(), session );

        // This is a work around
        System.out.printf("  sampleSingleton = %s *BEFORE*\n", sampleSingleton );
        if ( sampleSingleton == null) {
            // Look up the object
            Context initialContext = null;
            try {
                initialContext = new InitialContext();
                Object obj = initialContext.lookup("java:global/mywebapp/SampleSingleton");
                System.out.printf("   obj=%s\n", obj);
                sampleSingleton = (SampleSingleton)obj;
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("  sampleSingleton = %s *AFTER*\n", sampleSingleton );
    }

    @OnClose
    public void close(Session session) {
        System.out.printf("%s.close() called session=%s\n", getClass().getSimpleName(), session);
        System.out.printf("  sampleSingleton = %s\n", sampleSingleton );
    }

    @OnMessage
    public String generateReply( String message ) {
        return String.format("%s - name: %s, counter: %d, message:%s",
            new Date(), sampleSingleton.getFullName(),
            sampleSingleton.count(), message.toUpperCase());
    }
}
