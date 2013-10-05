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
import javax.ejb.Startup;
import javax.ejb.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type UserRegistry
 *
 * @author Peter Pilgrim (peter)
 */
@Singleton
@Startup
public class UserRegistry {

    private ConcurrentMap<String,User> registeredUsers = new ConcurrentHashMap<>();

    public void addUser( User user ) {
        registeredUsers.put( user.getLoginName(), user );
    }

    public void removeUser( User user ) {
        registeredUsers.remove(user.getLoginName());
    }

    public User findUser( String loginName ) {
        return registeredUsers.get(loginName);
    }

    public List<User> getUsers( ) {
        List<User> users =  new ArrayList<>(registeredUsers.values());
        Collections.sort(users);
        return users;
    }


    @PostConstruct
    public void postConstruct() {
        System.out.printf("**** "+getClass().getSimpleName()+".postConstruct() called %s\n", this );
    }

    @PreDestroy
    public void preDestroy() {
        System.out.printf("**** "+getClass().getSimpleName()+".preDestroy() called %s\n", this);
    }

}
