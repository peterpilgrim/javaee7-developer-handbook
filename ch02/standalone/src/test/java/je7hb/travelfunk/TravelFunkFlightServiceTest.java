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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import je7hb.standalone.Economy;
import je7hb.standalone.Premium;
import org.junit.*;

/**
 * The type TravelFunkFlightServiceTest
 * <p/>
 * User: Peter Pilgrim peterpilgrim
 * Date: 28/09/2012 - 17:17
 */
@Premium
public class TravelFunkFlightServiceTest {

    @Test
    public void shouldRetrieveFlightSet() {
        TravelService service = new TravelFunkServiceImpl();
        FlightSet flight = service.findRandomByAirline("BRS");
        assertNotNull( flight );
        System.out.printf("flight=%s\n", flight);
        assertThat( flight.getRoutes().size(), is(2) );
        assertThat( flight.getRoutes().get(0).getAirline().getShortName(), is("BRS"));
        assertThat( flight.getRoutes().get(1).getAirline().getShortName(), is("BRS"));
    }
}
