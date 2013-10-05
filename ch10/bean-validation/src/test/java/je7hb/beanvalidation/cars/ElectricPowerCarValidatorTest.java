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

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Verifies the operation of the CarValidatorTest
 *
 * @author Peter Pilgrim
 */
public class ElectricPowerCarValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldCompleteValidateElectricPoweredCar() {
        ElectricPoweredCar car =
            new ElectricPoweredCar(
                "Lotus Electric", 2, "LOT777", 1799,
                    31.25, 150.0 );
        Set<ConstraintViolation<ElectricPoweredCar>> constraintViolations
                = validator.validate(car, BasicCheck.class,
                    CompleteCheck.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }


    @Test
    public void shouldNotCompleteValidateElectricPoweredCar() {
        ElectricPoweredCar car =
                new ElectricPoweredCar(
                        "Lotus Electric", 2, "LOT777", 1799,
                        10, 12.0 );
        Set<ConstraintViolation<ElectricPoweredCar>> constraintViolations
                = validator.validate(car, BasicCheck.class,
                    CompleteCheck.class );
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(2, constraintViolations.size());
    }
}
