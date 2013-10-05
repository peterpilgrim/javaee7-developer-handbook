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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;

/**
 * The type RegisteredUserResource
 *
 * @author Peter Pilgrim (peter)
 */
@Path("/users")
@Stateless
public class RegisteredUserResource {

    @EJB
    private UserRegistry userRegistry;

    @GET
    @Produces("text/csv")
    public String listUsers() {
        System.out.printf("***DEBUG*** listUsers()\n" );
        System.out.printf("    userRegistry=%s\n", userRegistry);
        StringBuilder buf = new StringBuilder();
        for ( User user : userRegistry.getUsers()) {
            buf.append( user.getLoginName() +","+user.getFirstName()+",");
            buf.append( user.getLastName()+","+user.getSecretCode()+"\n");
        }
        return buf.toString();
    }

    @GET
    @Path("{id}")
    @Produces("text/csv")
    public String getUser( @PathParam("id") String loginName ) {
        System.out.printf("***DEBUG*** getUser( %s )\n", loginName );
        System.out.printf("    userRegistry=%s\n", userRegistry);
        User user = userRegistry.findUser(loginName);
        if ( user == null ) {
            return "";
        }
        else {
            StringBuilder buf = new StringBuilder();
            buf.append( user.getLoginName() +","+user.getFirstName()+",");
            buf.append( user.getLastName()+","+user.getSecretCode()+"\n");
            return buf.toString();
        }
    }

    @POST
    @Path("{id}")
    public void addUser( @PathParam("id") String loginName,
                         @FormParam("firstName") String fname,
                         @FormParam("lastName") String lname,
                         @FormParam("secretCode") int code )
    {
        System.out.printf("***DEBUG*** addUser( %s, %s, %s, %d)\n", loginName, fname, lname, code );
        System.out.printf("    userRegistry=%s\n", userRegistry);
        User user = new User(loginName,fname,lname,code);
        userRegistry.addUser(user);
    }

    @PUT
    @Path("{id}")
    public void amendUser( @PathParam("id") String loginName,
                           @FormParam("firstName") String fname,
                           @FormParam("lastName") String lname,
                           @FormParam("secretCode") int code )
    {
        System.out.printf("***DEBUG*** amendUser( %s, %s, %s, %d)\n", loginName, fname, lname, code );
        System.out.printf("    userRegistry=%s\n", userRegistry);
        User user = userRegistry.findUser(loginName);
        if ( user == null ) {
            throw new UnknownUserException("unknown login name: ["+loginName+"]");
        }
        else {
            User user2 = new User( user.getLoginName(), fname, lname, code );
            userRegistry.addUser(user2);
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteUser( @PathParam("id") String loginName) {
        System.out.printf("***DEBUG*** deleteUser( %s )\n", loginName );
        System.out.printf("    userRegistry=%s\n", userRegistry);
        User user = userRegistry.findUser(loginName);
        if ( user == null ) {
            throw new UnknownUserException("unknown login name: ["+loginName+"]");
        }
        else {
            userRegistry.removeUser(user);
        }
    }

}
