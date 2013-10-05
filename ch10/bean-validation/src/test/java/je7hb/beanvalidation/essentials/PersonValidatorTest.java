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

package je7hb.beanvalidation.essentials;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * A unit test PersonValidatorTest to verify the operation of PersonValidatorTest
 *
 * @author Peter Pilgrim
 */
public class PersonValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validatePerson() {
        Person person = new Person("Sazanne", "Abdiman", 34 );
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validatePersonMissingFirstName() {
        Person person = new Person(null, "Abdiman", 34 );
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void validatePersonMissingWrongAge() {
        Person person = new Person("Kieran", "Abdiman", 16 );
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(1, constraintViolations.size());
    }
}
