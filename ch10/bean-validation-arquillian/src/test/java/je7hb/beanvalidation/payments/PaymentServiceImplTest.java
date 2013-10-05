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

package je7hb.beanvalidation.payments;

import static org.junit.Assert.*;

import javax.ejb.*;
import javax.validation.ConstraintViolationException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

/**
 * Verifies the operation of the PaymentServiceImplTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class PaymentServiceImplTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(
                        PaymentServiceImpl.class,
                        SecureReceipt.class,
                        SecureReceiptValidator.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));

        System.out.println(jar.toString(true));
        return jar;
    }

    @EJB
    private PaymentServiceImpl serviceBean;

    @Test
    public void shouldFailValidateAllPaymentInputConstraints() {
        try{
            serviceBean.payEntity(null,null,null);
            fail("expected "+ConstraintViolationException.class.getName());
        }
        catch (EJBException ex) {
            if ( ex.getCause() instanceof ConstraintViolationException ) {
                ConstraintViolationException cve =
                    (ConstraintViolationException)ex.getCause();
                cve.printStackTrace(System.err);
                assertEquals(3, cve.getConstraintViolations().size() );
            }
            else {
                throw ex;
            }
        }
    }


    @Test
    public void shouldValidateAllPaymentConstraints() {
        serviceBean.payEntity(
            new Account("10002000",
                new BigDecimal("400.00")),
            "Bridgetown Markets", new BigDecimal("250.00"));
    }


    @Test(expected = EJBException.class)
    public void shouldNotValidatePaymentWithReturnConstraint() {
        serviceBean.payEntity(
            new Account("10002000",
                new BigDecimal("1000.00"))
            , "Clear Ridge", new BigDecimal("750000.00"));
    }
}
