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

package je7hb.beanvalidation.cars;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import je7hb.beanvalidation.cars.BasicCheck;
import je7hb.beanvalidation.cars.Car;
import je7hb.beanvalidation.cars.CompleteCheck;
import org.junit.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Verifies the operation of the CarValidatorTest
 *
 * @author Peter Pilgrim
 */
public class CarValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldBasicValidateCar() {
        Car car = new Car("Austin Martin", 0, "AM12457", 0 );
        Set<ConstraintViolation<Car>> constraintViolations
                = validator.validate(car, BasicCheck.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldCompleteValidateCar() {
        Car car = new Car("Bentley", 4, "BY4823", 2560 );
        Set<ConstraintViolation<Car>> constraintViolations
                = validator.validate(car, BasicCheck.class,
                    CompleteCheck.class);
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldNotCompleteValidateCar() {
        Car car = new Car("Sedaca", 0, "XYZ1234", 0 );
        Set<ConstraintViolation<Car>> constraintViolations
                = validator.validate(car, BasicCheck.class,
                    CompleteCheck.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(2, constraintViolations.size());
    }
}
