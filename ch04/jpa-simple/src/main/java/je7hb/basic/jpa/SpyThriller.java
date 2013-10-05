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

/**
 * The type SpyThriller
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="SPY_THRILLER")
public class SpyThriller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="BOOK_ID", nullable = false,
            insertable = true, updatable = true,
            table = "SPY_THRILLER")
    private long id;
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "AUTHORS", nullable = false)
    private String writer;
    @Column(name="BOOK_YEAR", nullable = false)
    private int year;
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Transient
    private String secretCode = Long.toString((long)(Math.random()*1.0E9));

    public SpyThriller() {
    }

    public SpyThriller(String writer, int year, String title ) {
        this.writer = writer;
        this.year = year;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecretCode() {
        return secretCode;
    }

    @Override
    public String toString() {
        return "SpyThriller{" +
            "id=" + id +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", year=" + year +
            ", secretCode = '"+secretCode +'\'' +
            '}';
    }
}
