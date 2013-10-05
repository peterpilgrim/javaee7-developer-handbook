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

package je7hb.jpa.advanced.entitygraph1;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * The type Contract
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Contract {
    @Id
    @Column(name="CONTRACT_ID")
    private long id;
    private String title;

    @Lob
    private String document;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private ConcertEvent concertEvent;

    public Contract() {
    }

    public Contract(long id, String title, String document) {
        this.id = id;
        this.title = title;
        this.document = document;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public ConcertEvent getConcertEvent() {
        return concertEvent;
    }

    public void setConcertEvent(ConcertEvent concertEvent) {
        this.concertEvent = concertEvent;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", document='" + StringHelper.systemIdentifierCode(document) + '\'' +
                ", concertEvent=" + StringHelper.systemIdentifierCode(concertEvent) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract)) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
