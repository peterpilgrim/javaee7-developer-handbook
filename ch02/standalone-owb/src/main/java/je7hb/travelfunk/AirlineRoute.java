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

import java.util.Date;

/**
 * The travel route for an airline an immmutable object
 */
public class AirlineRoute {
    private final String source;
    private final String target;
    private final Date departure;
    private final Date arrival;
    private final Airline airline;
    private final double price;

    public AirlineRoute(String source, String target, Date departure, Date arrival, Airline airline, double price) {
        this.source = source;
        this.target = target;
        this.departure = departure;
        this.arrival = arrival;
        this.airline = airline;
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public Date getDeparture() {
        return departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public Airline getAirline() {
        return airline;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "AirlineRoute{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", airline=" + airline +
                ", price="+String.format("%6.2f", price ) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirlineRoute)) return false;

        AirlineRoute that = (AirlineRoute) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (airline != null ? !airline.equals(that.airline) : that.airline != null) return false;
        if (arrival != null ? !arrival.equals(that.arrival) : that.arrival != null) return false;
        if (departure != null ? !departure.equals(that.departure) : that.departure != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = source != null ? source.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
