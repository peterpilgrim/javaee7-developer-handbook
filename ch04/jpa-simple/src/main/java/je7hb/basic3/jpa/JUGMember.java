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

package je7hb.basic3.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type JUGMember
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@NamedQueries( {
    @NamedQuery( name="JUGMember-findAllMembers",
        query = "select object(m) from JUGMember m"
    ),
    @NamedQuery( name="JUGMember-findByFirstAndLastName",
        query = "select object(m) from JUGMember m " +
                "where m.firstname = :firstname and " +
                "m.lastname = :lastname"
    ),
    @NamedQuery( name="JUGMember-findByLanguage",
        query = "select object(m) from JUGMember m "+
                "where m.language = :language"
    ),
    @NamedQuery( name="JUGMember-findByExperience",
    query = "select object(m) from JUGMember m "+
            "where m.experience = ?1"
)
})
public class JUGMember implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long memberId;
    private String firstname;
    private String lastname;
    private String language;
    private String country;
    private int experience;

    public JUGMember() {
        this(null,null, "Java", 0);
    }

    public JUGMember(String firstname, String lastname, String language,  int experience) {
        this( firstname, lastname, language, "England", experience );
    }

    public JUGMember(String firstname, String lastname, String language, String country, int experience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.language = language;
        this.country = country;
        this.experience = experience;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JUGMember)) return false;

        JUGMember jugMember = (JUGMember) o;

        if (experience != jugMember.experience) return false;
        if (memberId != jugMember.memberId) return false;
        if (country != null ? !country.equals(jugMember.country) : jugMember.country != null) return false;
        if (firstname != null ? !firstname.equals(jugMember.firstname) : jugMember.firstname != null) return false;
        if (language != null ? !language.equals(jugMember.language) : jugMember.language != null) return false;
        if (lastname != null ? !lastname.equals(jugMember.lastname) : jugMember.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (memberId ^ (memberId >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + experience;
        return result;
    }

    @Override
    public String toString() {
        return "JUGMember{" +
                "memberId=" + memberId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", experience=" + experience +
                '}';
    }
}
