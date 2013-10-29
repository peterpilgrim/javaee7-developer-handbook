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
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type SampleSingleton
 *
 * @author Peter Pilgrim
 */
@Singleton
@Startup
public class SampleSingleton {
    private AtomicInteger counter = new AtomicInteger(5000);
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

    public int count() {
        return counter.getAndAdd(2);
    }

    public String getFullName() {
        return "Peter Pilgrim";
    }
}
