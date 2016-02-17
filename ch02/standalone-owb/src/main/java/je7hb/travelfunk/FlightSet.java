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

import java.lang.instrument.UnmodifiableClassException;
import java.util.Collections;
import java.util.List;

/**
 * The type FlightSet
 * User: Peter Pilgrim
 */
public class FlightSet {

    private final List<AirlineRoute> routes;

    public FlightSet(List<AirlineRoute> routes) {
        this.routes = Collections.unmodifiableList(routes);
    }

    public List<AirlineRoute> getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "FlightSet{" +
                "routes=" + routes +
                '}';
    }
}
