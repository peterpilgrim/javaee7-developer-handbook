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

package je7hb.jpa.advanced.maps1;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * The type Album
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Album {
    @Id
    @Column(name="ALBUM_ID")
    private int albumId;

    @Column(nullable = false, name = "ALBUM_TITLE")
    @NotEmpty
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ALBUM_ARTIST")
    private Artist artist;

    @Column(nullable = false, name="RELEASE_DATE")
    @Past
    @Temporal(TemporalType.DATE)
    private Date date;

    public Album() {
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                ", date=" + date +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;

        Album album = (Album) o;

        if (albumId != album.albumId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return albumId;
    }
}
