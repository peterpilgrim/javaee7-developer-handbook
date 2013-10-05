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
 * The type Train1
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Train1 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "FROM_LOC", nullable = false)
    private String from;

    @Column(name = "TO_LOC", nullable = false)
    private String to;

    /** Pattern format yyyyMMdd */
    @Column(name = "TRAIN_DATE")
    private int trainDate;

    public Train1() { this(null,null,0); }

    public Train1(String from, String to, int trainDate) {
        this.from = from;
        this.to = to;
        this.trainDate = trainDate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFrom() { return from; }
    public void setFrom(String fromLoc) { this.from = fromLoc;
    }

    public String getTo() { return to; }
    public void setTo(String toLoc) { this.to = toLoc; }

    public int getTrainDate() { return trainDate; }
    public void setTrainDate(int trainDate) {
        this.trainDate = trainDate;
    }

    @Override
    public String toString() {
        return "Train1{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trainDate=" + trainDate +
                '}';
    }
}
