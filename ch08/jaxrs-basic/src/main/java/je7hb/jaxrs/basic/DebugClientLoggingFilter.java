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

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

/**
 * The type DebugClientLoggingFilter
 *
 * @author Peter Pilgrim (peter)
 */
@Provider
public class DebugClientLoggingFilter
    implements ClientRequestFilter, ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext reqCtx) throws IOException {
        System.out.printf("**** DEBUG CLIENT REQUEST ****\n");
        System.out.printf("uri: %s\n", reqCtx.getUri());
        if ( reqCtx.getEntity() != null ) {
            System.out.printf("entity: %s\n",
                reqCtx.getEntity().getClass().getName() + "@" +
                Integer.toHexString(
                    System.identityHashCode( reqCtx.getEntity())));
        }
        System.out.printf("method: %s\n", reqCtx.getMethod());
        System.out.printf("mediaType: %s\n", reqCtx.getMediaType());
        System.out.printf("date: %s\n", reqCtx.getDate());
        System.out.printf("language: %s\n", reqCtx.getLanguage());
        for (String name: reqCtx.getHeaders().keySet()) {
            System.out.printf("header[%s] => %s\n",
                    name, reqCtx.getHeaderString(name) );
        }
        for (String name: reqCtx.getCookies().keySet()) {
            System.out.printf("cookie[%s] => %s\n",
                    name, reqCtx.getHeaderString(name) );
        }
        System.out.printf("**** END CLIENT REQUEST ****\n\n");
    }

    @Override
    public void filter(ClientRequestContext reqCtx,
                       ClientResponseContext resCtx) throws IOException {
        System.out.printf("**** DEBUG CLIENT RESPONSE ****\n");
        System.out.printf("status: %s\n", resCtx.getStatus());
        System.out.printf("status info: %s\n", resCtx.getStatusInfo());
        System.out.printf("length: %s\n", resCtx.getLength());
        System.out.printf("mediaType: %s\n", resCtx.getMediaType());
        System.out.printf("date: %s\n", resCtx.getDate());
        System.out.printf("language: %s\n", resCtx.getLanguage());
        for (String name: resCtx.getHeaders().keySet()) {
            System.out.printf("header[%s] => %s\n",
                    name, resCtx.getHeaderString(name) );
        }
        for (String name: resCtx.getCookies().keySet()) {
            System.out.printf("cookie[%s] => %s\n",
                    name, resCtx.getHeaderString(name) );
        }
        System.out.printf("**** END CLIENT RESPONSE ****\n\n");
    }
}
