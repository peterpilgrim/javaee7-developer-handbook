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

import je7hb.standalone.Premium;

import java.text.SimpleDateFormat;
import java.util.*;

import static je7hb.travelfunk.Utils.*;

/**
 * The type TravelFunkServiceImpl
 *
 * @author Peter Pilgrim
 */
@Premium
public class TravelFunkServiceImpl implements TravelService {

    @Override
    public FlightSet findRandomByAirline(String airline) {
        Airline airlineBrit =
                new Airline("BRS","British Stars");
        return new FlightSet(
            Arrays.asList(
                new AirlineRoute(
                    "LGW", "NYC",
                    parseDate("20121110-07:30:00 BST"),
                    parseDate("20121110-14:00:00 -0500"),
                        airlineBrit, 330.0
                ),
                new AirlineRoute(
                        "NYC", "SFO",
                        parseDate("20121110-16:45:00 -0500"),
                        parseDate("20121110-20:00:00 -0700"),
                        airlineBrit, 250.0
                )
            )
        );
    }
}
