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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * The type RestfulBookService
 *
 * @author Peter Pilgrim (peter)
 */
@Path("/books")
public class RestfulBookService {

    private List<Book> products = Arrays.asList(
            new Book("Sir Arthur Dolan Coyle", "Sherlock Holmes and the Hounds of the Baskerville"),
            new Book("Dan Brown", "Da Vinci Code"),
            new Book("Charles Dickens", "Great Expectations"),
            new Book("Robert Louis Stevenson", "Treasure Island"));

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getList() {
        StringBuffer buf = new StringBuffer();
        for (Book b: products) { buf.append(b.title); buf.append('\n'); }
        return buf.toString();
    }

    @PostConstruct
    public void acquireResource() {
        System.out.println( this.getClass().getSimpleName()+"#acquireResource()" );
    }

    @PreDestroy
    public void releaseResource() {
        System.out.println( this.getClass().getSimpleName()+"#releaseResource()" );
    }

    static class Book {
        public final String author;
        public final String title;

        Book(String author, String title) {
            this.author = author;
            this.title = title;
        }
    }
}

