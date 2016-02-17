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

import je7hb.standalone.Economy;
import je7hb.standalone.Premium;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

/**
 * Verifies the operation of the TravelServiceTest
 *
 * @author Peter Pilgrim
 */
@RunWith(CdiTestRunner.class)
public class TravelServiceTest {

    @Inject @Premium TravelService premiumTravelService;
    @Inject @Economy TravelService economyTravelService;

    @Test
    public void shouldInjectPremiumService() {
        System.out.printf("premiumTravelService=%s\n", premiumTravelService);
        assertNotNull(premiumTravelService);
        FlightSet flight = premiumTravelService.findRandomByAirline("BRS");
        assertNotNull( flight );
    }

    @Test
    public void shouldInjectEconomyService() {
        System.out.printf("economy=%s\n", economyTravelService);
        assertNotNull(economyTravelService);
        FlightSet flight = economyTravelService.findRandomByAirline("BRS");
        assertNotNull( flight );
    }
}
