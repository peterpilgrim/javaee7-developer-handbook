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
import javax.json.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type RestfulBookService
 *
 * @author Peter Pilgrim (peter)
 */
@Path("/hyperbooks")
public class RestfulBookServiceWithHypermedia {

    private List<HyperBook> products = Arrays.asList(
        new HyperBook(101,"Sir Arthur Dolan Coyle",
            "Sherlock Holmes and the Hounds of the Baskervilles"),
        new HyperBook(102,"Dan Brown",
            "Da Vinci Code"),
        new HyperBook(103,"Charles Dickens",
            "Great Expectations"),
        new HyperBook(104,"Robert Louis Stevenson",
            "Treasure Island"));
    private final JsonBuilderFactory factory;

    public RestfulBookServiceWithHypermedia() {
        factory = Json.createBuilderFactory(null);
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
//    @Produces({"text/xml"})
    public Response getProduct(@PathParam("id")int id) {

        System.out.printf("Hyperbooks id=%s\n", id);
        HyperBook product = null;
        for ( HyperBook book: products ) {
            if ( book.id == id ) {
                product = book; break;
            }
        }
        if ( product == null)
            throw new RuntimeException("book not found");
        return Response.ok(product.asJsonObject())
            .link("http://localhost:8080/order/"+
                id+"/warehouse", "stock")
            .build();
    }

    @GET
    @Produces({"application/json"})
    public Response getProductList() {

        JsonObjectBuilder builder = factory.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();

        List<Link> links = new ArrayList<>();
        for ( HyperBook book: products ) {
            arrayBuilder.add( book.asJsonObject() );
            links.add(
                Link.fromPath("http://localhost:8080/ordering/" +
                    book.id + "/shipment")
                    .rel("ship")
                    .build());
        }
        builder.add("products", arrayBuilder.build());

        return Response.ok(builder.build())
                .links( links.toArray( new Link[]{}))
                .build();
    }

    @PostConstruct
    public void acquireResource() {
        System.out.println(this.getClass().getSimpleName() + "#acquireResource()");
    }

    @PreDestroy
    public void releaseResource() {
        System.out.println( this.getClass().getSimpleName()+"#releaseResource()" );
    }

    static class HyperBook {
        public final int id;
        public final String author, title;

        HyperBook(int id, String author, String title) {
            this.id = id;
            this.author = author;
            this.title = title;
        }

        public JsonObject asJsonObject() {
            JsonBuilderFactory    jsonBuilderFactory = Json.createBuilderFactory(null);
            JsonObjectBuilder builder = jsonBuilderFactory.createObjectBuilder();

            JsonObject json =
                builder.add("id", id)
                    .add("author", author )
                    .add("title", title)
                    .build();
            return json;
        }

        public String asXML() {
            return "<book id=\""+id+"\">" +
                "<author>" + author + "</author"+
                "<title>" + title + "</title"+
                "</book>";
        }
    }
}

