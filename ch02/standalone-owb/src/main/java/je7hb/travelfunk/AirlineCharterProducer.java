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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type AirlineCharterProducer
 *
 * @author Peter Pilgrim (peter)
 */
@ApplicationScoped
public class AirlineCharterProducer {
    private List<Airline> airlines = new ArrayList<Airline>();
    private AtomicInteger counter = new AtomicInteger(100);

    @Produces
    public Airline createAirline() {

        final int code = counter.addAndGet(2);
        final String shortCode = String.format("UKAIR%03d", code );
        final String longDesc = String.format("UK AIR Charter %03d", code );
        Airline newAirline = new Airline(shortCode,longDesc);
        airlines.add(newAirline);
        return newAirline;
    }
    public void disposeAirline(@Disposes Airline bar) {
        airlines.remove(bar);
    }
}
