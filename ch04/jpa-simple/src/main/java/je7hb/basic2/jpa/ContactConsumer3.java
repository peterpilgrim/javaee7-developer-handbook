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
 * The type ContactConsumer3
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class ContactConsumer3 implements Serializable {
    @EmbeddedId
    private Contact3PK contact;
    private String location;

    public ContactConsumer3() {
        this( new Contact3PK(), null);
    }

    public ContactConsumer3(Contact3PK contact, String location) {
        this.contact = contact;
        this.location = location;
    }

    public Contact3PK getContact() { return contact; }
    public void setContact(Contact3PK contact) { this.contact = contact; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactConsumer3)) return false;

        ContactConsumer3 that = (ContactConsumer3) o;

        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contact != null ? contact.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactConsumer3{" +
                "contact=" + contact +
                ", location='" + location + '\'' +
                '}';
    }
}
