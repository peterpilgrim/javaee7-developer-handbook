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
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * The type AddExtraUserAgentFilter
 *
 * @author Peter Pilgrim (peter)
 */
@Provider
@PreMatching
public class AddExtraUserAgentFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext context)
            throws IOException {
        System.out.printf("%s.filter() thread: [%s] context=%s\n",
                getClass().getSimpleName(), Thread.currentThread(), context );
        String userAgent = context.getHeaderString("User-Agent");
        System.out.printf("userAgent=%s\n", userAgent );
        if ( userAgent != null ) {
            context.getHeaders().putSingle("X-User-Agent-Copy", userAgent );
        }
    }
}
