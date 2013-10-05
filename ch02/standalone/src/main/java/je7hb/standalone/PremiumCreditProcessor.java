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

package je7hb.standalone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * The type PremiumCreditProcessor
 *
 * @author Peter Pilgrim (peter)
 */
@Premium
public class PremiumCreditProcessor implements CreditProcessor {

    @Override
    public void check(String account) {
        if ( !account.trim().startsWith("1234")) {
            throw new RuntimeException("account:["+account+"] is not valid!");
        }
    }

    @PostConstruct
    public void acquireResource() {
        System.out.println( this.getClass().getSimpleName()+"#acquireResource()" );
    }

    @PreDestroy
    public void releaseResource() {
        System.out.println("===================================================");
        System.out.println( this.getClass().getSimpleName()+"#releaseResource()" );
        System.out.println("===================================================");
    }
}
