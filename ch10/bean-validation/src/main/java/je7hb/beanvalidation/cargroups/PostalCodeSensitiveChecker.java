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

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

/**
 * The type PostalCodeSensitiveChecker
 *
 * @author Peter Pilgrim (peter)
 */
@Documented
@Constraint(validatedBy = PostalCodeSensitiveValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface PostalCodeSensitiveChecker {

    String countryCode() default "gb";

    String message() default "{je7hb.beanvalidation.cargroups.PostCodeCoherenceChecker.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several @PostCode annotations on the same element
     * @see {@link PostalCode}
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PostalCode[] value();
    }
}