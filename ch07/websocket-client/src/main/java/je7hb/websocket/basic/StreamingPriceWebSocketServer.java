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

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigDecimal;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * The type EchoWebSocketServer
 *                                                                                                                ≤§
 * @author Peter Pilgrim
 */
@ApplicationScoped
@ServerEndpoint("/streamingPrice")
public class StreamingPriceWebSocketServer {
    @Resource(name = "concurrent/ScheduledTasksExecutor")
    ManagedScheduledExecutorService executorService;
    private Object lock = new Object();
    private BigDecimal price = new BigDecimal("1000.0");
    private BigDecimal unitPrice =  new BigDecimal("0.01");

    @OnOpen
    public void openRemoteConnection( final Session session) {
        System.out.printf("%s.openRemoteConnection( session = [%s], ",
            getClass().getSimpleName(), session);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.printf("%s.run( session = [%s], %s\n",
                        getClass().getSimpleName(), session, price);
                    session.getBasicRemote().sendText(
                        "PRICE = " + price);
                    synchronized (lock) {
                        if (Math.random() < 0.5) {
                            price = price.subtract(unitPrice);
                        } else {
                            price = price.add(unitPrice);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }, 500, 500, MILLISECONDS);
    }

}

