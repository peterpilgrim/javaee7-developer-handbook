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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The type SampleResponse
 *
 * @author Peter Pilgrim (peter)
 */
public class SampleResponse {

    public Response generateSimpleOk() {
        return Response.ok().build();
    }

    public Response generateSimpleOkWithEntity() {
        return Response.ok().entity("This is message")
                .type(MediaType.TEXT_PLAIN_TYPE).build();
    }

    public Response generateSimpleOkWithEntityXml() {
        return Response.ok().entity("<msg>This is message</msg>")
                .type(MediaType.TEXT_XML_TYPE).build();
    }

    public Response generateSimpleOkWithGermanyLang() {
        return Response.ok()
                .language("de_de")
                .entity("<msg>Einfaches boetschaft</msg>")
                .type(MediaType.TEXT_XML_TYPE).build();
    }

    public Response generateUnauthorisedError() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .build();
    }

    public Response generateUnauthorisedWithEntityXml() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("<msg>Unauthorised</msg>")
                .type(MediaType.TEXT_XML_TYPE).build();
    }
}
