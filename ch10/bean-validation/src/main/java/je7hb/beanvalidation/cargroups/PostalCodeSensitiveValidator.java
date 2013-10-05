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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type PostalCodeSensitiveValidator
 *
 * @author Peter Pilgrim (peter)
 */
public class PostalCodeSensitiveValidator
implements ConstraintValidator<PostalCodeSensitiveChecker,Address> {
    @Override
    public void initialize(PostalCodeSensitiveChecker constraintAnnotation) {
    }

    @Override
    public boolean isValid(Address value, ConstraintValidatorContext context) {
        System.out.printf("%s.isValid( %s, %s)\n",
                getClass().getSimpleName(), value, context );
        return value.getPostCode().startsWith("SW1 ");
    }
}
