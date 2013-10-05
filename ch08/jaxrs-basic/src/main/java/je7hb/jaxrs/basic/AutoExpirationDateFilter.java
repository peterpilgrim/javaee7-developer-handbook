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

package je7hb.jaxrs.basic;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type AutoExpirationDateFilter
 *
 * @author Peter Pilgrim (peter)
 */
@Provider
public class AutoExpirationDateFilter implements ContainerResponseFilter{

    private static SimpleDateFormat formatter =
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    @Override
    public void filter(ContainerRequestContext reqCtx,
                       ContainerResponseContext resCtx)
    throws IOException {
        if ( reqCtx.getMethod().equals("GET")) {
            Date oneHour = new Date(
                    System.currentTimeMillis() + 60* 1000 );
            resCtx.getHeaders().add("Expires",
                    formatter.format( oneHour));
        }
    }
}
