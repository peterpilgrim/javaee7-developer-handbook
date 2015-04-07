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
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The type RestfulBookService
 *
 * @author Peter Pilgrim (peter)
 */
@Path("/async/books")
@Stateless
public class RestfulBookAsyncService {

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    private List<Book> products = Arrays.asList(
            new Book("Miguel De Cervantes", "Don Quixote"),
            new Book("Daniel Defoe", "Robinson Crusoe"),
            new Book("Jonathan Swift", "Gulliver's Travels"),
            new Book("Mary Shelley", "Frankenstein"),
            new Book("Charlotte Bronte", "Jane Eyre"));

    // Replace with Lambdas in Java 8
    class MyAsyncTask implements Runnable {

        final private AsyncResponse asyncResponse;

        public MyAsyncTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        public void run() {
            System.out.printf("%s.run() thread: [%s]\n",
                    getClass().getSimpleName(), Thread.currentThread());
            final long SLEEP=500;
            final int N=10;
            asyncResponse.setTimeout( (N+1)*SLEEP, TimeUnit.MILLISECONDS );
            System.out.printf("retrieve list asynchronously ");
            try {
                for (int j=0; j<N; ++j ) {
                    System.out.print(".");
                    System.out.flush();
                    Thread.sleep(SLEEP);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(".\n");

            StringBuffer buf = new StringBuffer();
            for (Book b: products) { buf.append(b.title); buf.append('\n'); }
            Response response = Response.ok(buf.toString()).build();
            System.out.printf("sending data back now on thread: [%s]\n",
                    Thread.currentThread());
            asyncResponse.resume(response);
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void getList( @Suspended AsyncResponse asyncResponse) {
        System.out.printf("%s.getList() thread: [%s]\n",
                getClass().getSimpleName(), Thread.currentThread());
        executor.submit( new MyAsyncTask(asyncResponse));
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

