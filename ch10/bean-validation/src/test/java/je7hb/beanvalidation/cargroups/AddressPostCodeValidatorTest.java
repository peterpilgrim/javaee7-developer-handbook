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

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A unit test AddressValidatorTest to verify the operation of AddressValidatorTest
 *
 * @author Peter Pilgrim
 */
public class AddressPostCodeValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldValidateWithSpecificAreaOnly() {
        Address addr = new Address();
        addr.setPostCode("SW1 AA");
        Set<ConstraintViolation<Address>> constraintViolations
                = validator.validate(addr, Address.AreaSensitive.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldNotValidateWithSpecificAreaOnly() {
        Address addr = new Address();
        addr.setPostCode("N11 4AD");
        Set<ConstraintViolation<Address>> constraintViolations
                = validator.validate(addr, Address.AreaSensitive.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void shouldValidateWithComplete() {
        Address addr = new Address();
        addr.setStreet1("1 Granger Avenue");
        addr.setCity("London");
        addr.setPostCode("SW1 3KG");
        Set<ConstraintViolation<Address>> constraintViolations
                = validator.validate(addr, Address.Complete.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }


    @Test
    public void shouldNotValidateWithComplete() {
        Address addr = new Address();
        addr.setStreet1("133 Bell Road");
        addr.setCity("London");
        addr.setPostCode("NW1 7SR");

        Set<ConstraintViolation<Address>> constraintViolations
                = validator.validate(addr, Address.Complete.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void shouldValidateWithCompleteButNotDefault() {
        Address addr = new Address();
        addr.setStreet1(null);
        addr.setCity(null);
        addr.setPostCode("SW1 1AA");

        Set<ConstraintViolation<Address>> constraintViolations
                = validator.validate(addr, Address.Complete.class );
        System.out.printf("**** constraint violations: %s\n", constraintViolations);
        assertEquals(2, constraintViolations.size());
    }
}
