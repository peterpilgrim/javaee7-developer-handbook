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

package je7hb.basic4.jpa.manytoone;

import javax.persistence.*;

/**
 * The type Clearance
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Clearance implements java.io.Serializable {
    @Id
    @Column(name="SEC_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int securityId;
    @Column(name="SEC_LEVEL", nullable = false)
    private String securityLevel;
    /* ... */

    public Clearance() {
        this(null);
    }

    public Clearance(String securityLevel) {
        this.securityLevel = securityLevel;
    }


    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clearance)) return false;

        Clearance clearance = (Clearance) o;

        if (securityId != clearance.securityId) return false;
        if (securityLevel != null ? !securityLevel.equals(clearance.securityLevel) : clearance.securityLevel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = securityId;
        result = 31 * result + (securityLevel != null ? securityLevel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Clearance{" +
                "securityId=" + securityId +
                ", securityLevel='" + securityLevel + '\'' +
                '}';
    }
}
