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
import javax.validation.constraints.*;

/**
 * The type LiveEvent
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="LIVE_EVENT")
@Inheritance
public class LiveEvent extends ConcertEvent{
    @Size(min=3)
    protected String stadium;
    @Min(10)
    protected int capacity;

    public LiveEvent() {
    }

    public LiveEvent(long id, String name, EventType eventType, Artist artist, String stadium, int capacity) {
        super(id, name, eventType, artist);
        this.stadium = stadium;
        this.capacity = capacity;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "LiveEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventType=" + eventType +
                ", artist=" + artist +
                ", contracts=" + contracts +
                ", stadium='" + stadium + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}

