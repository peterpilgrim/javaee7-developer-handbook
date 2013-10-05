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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type PostalCodeValidator
 *
 * @author Peter Pilgrim (peter)
 */
public class PostalCodeValidator implements ConstraintValidator<PostalCode,String> {
    private String country;

    private Pattern pattern =
            Pattern.compile(
                    "[A-Z][A-Z]\\d{1,2}[ \t]*\\d{1,2}[A-Z][A-Z]");

    @Override
    public void initialize(PostalCode postalCode) {
        this.country = postalCode.country();
    }

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {
        System.out.printf("%s.isValid( %s, %s)\n",
                getClass().getSimpleName(), value, context );
        if ( value == null) {
            return true;
        }
        Matcher m = pattern.matcher(value.toUpperCase());
        return m.matches();
    }
}
