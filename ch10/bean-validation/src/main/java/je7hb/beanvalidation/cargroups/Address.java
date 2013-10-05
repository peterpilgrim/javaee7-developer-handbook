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

package je7hb.beanvalidation.cargroups;

import je7hb.beanvalidation.essentials.PostalCode;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import javax.validation.groups.Default;

/**
 * The type Address
 *
 * @author Peter Pilgrim (peter)
 */
@PostalCodeSensitiveChecker(groups = Address.AreaSensitive.class)
public class Address {
    @NotNull
    @Size(max = 50)
    private String street1;

    @Size(max = 50)
    private String street2;

    @NotNull @PostalCode
    private String postCode;

    @NotNull @Size(max = 30)
    private String city;

    /**
     * check coherence on the overall object
     * Needs basic checking to be green first
     */
    public interface AreaSensitive {}

    /**
     * check both basic constraints and high level ones.
     * high level constraints are not checked if basic constraints fail
     */
    @GroupSequence({AreaSensitive.class, Default.class})
    public interface Complete {}

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street1) {
        this.street1 = street1;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
