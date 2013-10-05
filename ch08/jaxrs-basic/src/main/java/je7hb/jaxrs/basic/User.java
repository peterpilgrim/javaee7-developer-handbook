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

/**
 * The type User
 *
 * @author Peter Pilgrim (peter)
 */
public final class User implements Comparable<User> {
    private final String loginName;
    private final String firstName;
    private final String lastName;
    private final int secretCode;

    public User(String loginName, String firstName,
                String lastName, int secretCode) {
        this.loginName = loginName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.secretCode = secretCode;
    }

    public String getLoginName() { return loginName; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public int getSecretCode() { return secretCode; }

    @Override
    public String toString() {
        return "User{" +
                "loginName='" + loginName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", secretCode=" + (secretCode > 0 ?"*****":"NONE") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!loginName.equals(user.loginName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return loginName.hashCode();
    }

    @Override
    public int compareTo(User ref) {
        return loginName.compareTo(ref.loginName);
    }
}


