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

import javax.enterprise.inject.Produces;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type HouseholdCredit
 *
 * @author Peter Pilgrim (peter)
 */
public class HouseholdCredit {

    private static AtomicInteger counter = new AtomicInteger(1000);
    @Produces
    @Economy
    public CreditProcessor createCreditProcessor() {
        return new StreetCreditProcessor("promoter"+counter.getAndIncrement());
    }

    /**
     * The type StreetCreditProcessor
     *
     * @author Peter Pilgrim (peter)
     */
    public static class StreetCreditProcessor implements CreditProcessor {
        private final String workerId;

        public StreetCreditProcessor(String workerId) {
            this.workerId = workerId;
        }

        @Override
        public void check(String account) {
        }

        @Override
        public String toString() {
            return "StreetCreditProcessor{" +
                    "workerId='" + workerId + '\'' +
                    '}';
        }
    }
}
