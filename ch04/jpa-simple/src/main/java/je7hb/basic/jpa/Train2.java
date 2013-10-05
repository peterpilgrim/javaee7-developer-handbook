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

package je7hb.basic.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Train2
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Train2 implements Serializable {
    private long id;
    private String from;
    private String to;
    private int trainDate;

    public Train2() { this(null,null,0); }

    public Train2(String from, String to, int trainDate) {
        this.from = from;
        this.to = to;
        this.trainDate = trainDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "FROM_LOC", nullable = false)
    public String getFrom() { return from; }
    public void setFrom(String fromLoc) { this.from = fromLoc;
    }

    @Column(name = "TO_LOC", nullable = false)
    public String getTo() { return to; }
    public void setTo(String toLoc) { this.to = toLoc; }

    /** Pattern format yyyyMMdd */
    @Column(name = "TRAIN_DATE")
    public int getTrainDate() { return trainDate; }
    public void setTrainDate(int trainDate) {
        this.trainDate = trainDate;
    }

    @Override
    public String toString() {
        return "Train2{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trainDate=" + trainDate +
                '}';
    }
}
