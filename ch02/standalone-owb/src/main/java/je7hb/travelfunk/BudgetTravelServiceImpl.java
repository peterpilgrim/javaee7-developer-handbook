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

import java.util.Arrays;

import static je7hb.travelfunk.Utils.parseDate;

/**
 * The type TravelFunkServiceImpl
 *
 * @author Peter Pilgrim
 */
@Economy
public class BudgetTravelServiceImpl implements TravelService {

    @Override
    public FlightSet findRandomByAirline(String airline) {
        Airline airlineBudget =
                new Airline("CHP","Cheap Budget");
        return new FlightSet(
            Arrays.asList(
                new AirlineRoute(
                    "LGW", "DUB",
                    parseDate("20121110-12:30:00 GMT"),
                    parseDate("20121110-14:00:00 GMT"),
                        airlineBudget, 69.0
                ),
                new AirlineRoute(
                        "LHW", "PAR",
                        parseDate("20121110-16:45:00 -0500 GMT"),
                        parseDate("20121110-20:00:00 -0700 +0100"),
                        airlineBudget, 79.0
                )
            )
        );
    }
}
