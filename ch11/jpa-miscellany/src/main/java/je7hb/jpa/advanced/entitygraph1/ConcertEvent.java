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
import java.util.ArrayList;
import java.util.List;

/**
 * The type ConcertEvent
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name = "CONCERT_EVENT")
public class ConcertEvent {
    @Id
    @Column(name="CONCERT_EVENT_ID")
    protected long id;

    @Column(nullable=false, name="ARTIST_NAME")
    protected String name;

    @OneToOne(cascade = CascadeType.ALL)
    protected EventType eventType;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Artist artist;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY )
    protected List<Contract> contracts = new ArrayList<>();

    public ConcertEvent() {
    }

    public ConcertEvent(long id, String name, EventType eventType, Artist artist) {
        this.id = id;
        this.name = name;
        this.eventType = eventType;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "ConcertEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventType=" + eventType +
                ", artist=" + artist +
                ", contracts=" + contracts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcertEvent)) return false;

        ConcertEvent that = (ConcertEvent) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
