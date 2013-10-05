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

package je7hb.travelfunk;

import org.junit.Test;

import javax.inject.Inject;

/**
 * The type AirlineCharterProducerTest
 *
 * @author Peter Pilgrim (peter)
 */
public class AirlineCharterProducerTest extends AbstractCdiContainerTest {


    private @Inject Airline charter;

    void printHelper() {
        System.out.printf("charter=%s\n", charter);
    }

    @Test
    public void shouldInjectManyCharterAirlines() {

        for ( int j=0; j<5; ++j ) {
            printHelper();
        }
    }
}
