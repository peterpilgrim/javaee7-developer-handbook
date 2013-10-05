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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type SecureReceiptValidator
 *
 * @author Peter Pilgrim
 */
public class SecureReceiptValidator
    implements ConstraintValidator<SecureReceipt,Receipt> {
    @Override
    public void initialize(SecureReceipt annotation) { }

    @Override
    public boolean isValid(Receipt value,
                           ConstraintValidatorContext context) {
        if ( value == null) return true;
        return value.getMessage().trim().startsWith("SEC");
    }
}
