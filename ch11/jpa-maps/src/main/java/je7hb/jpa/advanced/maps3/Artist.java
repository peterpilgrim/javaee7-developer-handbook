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
import java.util.HashMap;
import java.util.Map;

/**
 * The type Artist
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Artist {
    @Id @Column(name="ARTIST_ID")
    private long id;

    @Column(nullable=false, name="ARTIST_NAME")
    private String name;

    @OneToMany(mappedBy="artist",cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name="EVENT_TYPE_ID")
    private Map<EventType, LiveEvent> events = new HashMap<>();

    public Artist() {
    }

    public Artist(long id, String name) {
        this.id = id;
        this.name = name;
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

    public Map<EventType, LiveEvent> getEvents() {
        return events;
    }

    public void setEvents(Map<EventType, LiveEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
