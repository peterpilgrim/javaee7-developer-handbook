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

package je7hb.jpa.advanced.maps3;

import javax.persistence.*;

/**
 * The type LiveEvent
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="LIVE_EVENT")
public class LiveEvent {
    @Id @Column(name="LIVE_EVENT_ID")
    private long id;

    @Column(nullable=false, name="ARTIST_NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private EventType eventType;

    @ManyToOne(cascade = CascadeType.ALL)
    private Artist artist;

    public LiveEvent() {
    }

    public LiveEvent(long id, String name, EventType eventType, Artist artist) {
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "LiveEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventType='" + eventType + '\'' +
                ", artist=" + Integer.toHexString(System.identityHashCode(artist)) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiveEvent)) return false;

        LiveEvent liveEvent = (LiveEvent) o;

        if (id != liveEvent.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
