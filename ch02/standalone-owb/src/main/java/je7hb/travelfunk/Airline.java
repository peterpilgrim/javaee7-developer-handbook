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

package je7hb.travelfunk;

/**
 * The type Airline
 *
 * @author Peter Pilgrim
 */
public class Airline {
    private final String shortName;
    private final String fullName;

    public Airline(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airline)) return false;

        Airline airline = (Airline) o;

        if (fullName != null ? !fullName.equals(airline.fullName) : airline.fullName != null) return false;
        if (shortName != null ? !shortName.equals(airline.shortName) : airline.shortName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shortName != null ? shortName.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }
}

