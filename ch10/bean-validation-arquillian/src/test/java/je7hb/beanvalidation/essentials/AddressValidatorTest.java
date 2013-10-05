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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A unit test AddressValidatorTest to verify the operation of AddressValidatorTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class AddressValidatorTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Address.class, Country.class,
                        PostalCodeValidator.class, PostalCode.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        return jar;
    }

// London SW1A 1AA, United Kingdom
//    Phone:020 7930 4832

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldValidate() {
        Country country = new Country();
        country.setIsoName("GB");

        Address addr = new Address();
        addr.setStreet1("Buckingham Palace");
        addr.setStreet2("");
        addr.setCity("London");
        addr.setPostalCode("SW1 1AA");
        addr.setCountry(country);
        assertNotNull(addr);

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldNotValidate() {

        Country country = new Country();
        country.setIsoName("GB");

        Address addr = new Address();
        addr.setStreet1("");
        addr.setCity("London");
        addr.setPostalCode("SW1 1AA");
        addr.setCountry(country);

        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
        System.out.printf("constraint violations: %s\n", constraintViolations);
        assertEquals(1, constraintViolations.size());
    }
}
