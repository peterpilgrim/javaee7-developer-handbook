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

package je7hb.servlets.simple;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * The type AppSessionListener
 *
 * @author Peter Pilgrim
 */
@WebListener
public class AppSessionListener implements HttpSessionListener, HttpSessionActivationListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.printf("sessionCreated() on %s with %s\n",
                getClass().getSimpleName(),  se );
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.printf("sessionDestroyed() on %s with %s\n",
                getClass().getSimpleName(),  se );
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.printf("sessionWillPassivate() on %s with %s\n",
                getClass().getSimpleName(),  se );
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.printf("sessionDidActivate() on %s with %s\n",
                getClass().getSimpleName(),  se );
    }
}
