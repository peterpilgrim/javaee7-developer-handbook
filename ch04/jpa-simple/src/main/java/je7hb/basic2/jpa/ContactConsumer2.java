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

package je7hb.basic2.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type ContactConsumer2
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@IdClass(Contact2PK.class)
public class ContactConsumer2 implements Serializable {
    @Id
    private long contactId;

    @Id
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstname;

    @Id
    @Column(name = "LAST_NAME", nullable = false)
    private String lastname;

    public ContactConsumer2() {
        this(0, null,null);
    }

    public ContactConsumer2(long contactId, String firstname, String lastname) {
        this.contactId = contactId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getContactId() { return contactId; }
    public void setContactId(long contactId) { this.contactId = contactId; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactConsumer2)) return false;

        ContactConsumer2 that = (ContactConsumer2) o;

        if (contactId != that.contactId) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (contactId ^ (contactId >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactConsumer2{" +
                "contactId=" + contactId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
