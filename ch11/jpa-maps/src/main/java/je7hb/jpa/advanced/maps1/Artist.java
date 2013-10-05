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
import java.util.HashMap;
import java.util.Map;

/**
 * The type Artist
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Artist {
    @Id
    @Column(name="ARTIST_ID")
    private int artistId;

    @NotEmpty
    @Column(nullable = false, name="ARTIST_NAME")
    private String artistName;

    @OneToMany(mappedBy="artist",cascade = CascadeType.ALL)
    @MapKey()
    private Map<Integer,Album> albums = new HashMap<>();

    public Artist() {
    }

    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Map<Integer, Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Map<Integer, Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;

        Artist artist = (Artist) o;

        if (artistId != artist.artistId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return artistId;
    }
}
