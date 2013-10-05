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

package je7hb.beanvalidation.cars;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

/**
 * The type ElectricPoweredCar
 *
 * @author Peter Pilgrim (peter)
 */
public class ElectricPoweredCar extends Car {
    @DecimalMin(value="25.0", groups={BasicCheck.class, CompleteCheck.class})
    private final double powerKiloWatts;
    @DecimalMin(value="100.0", groups={BasicCheck.class, CompleteCheck.class})
    private final double rangeInMiles;

    public ElectricPoweredCar(
            String carMaker, int seats, String licensePlate,
            int engineSize, double powerKiloWatts,
            double rangeInMiles) {
        super(carMaker, seats, licensePlate, engineSize);
        this.powerKiloWatts = powerKiloWatts;
        this.rangeInMiles = rangeInMiles;
    }

    public double getPowerKiloWatts() {
        return powerKiloWatts;
    }

    public double getRangeInMiles() {
        return rangeInMiles;
    }

    @Override
    public String toString() {
        return "ElectricPoweredCar{" +
            "carMaker='" + getCarMaker() + '\'' +
            ", seats=" + getSeats() +
            ", licensePlate='" + getLicensePlate() + '\'' +
            ", engineSize=" + getEngineSize() +
            ", powerKiloWatts=" + powerKiloWatts +
            ", rangeInMiles=" + rangeInMiles +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricPoweredCar)) return false;
        if (!super.equals(o)) return false;

        ElectricPoweredCar that = (ElectricPoweredCar) o;

        if (Double.compare(that.powerKiloWatts, powerKiloWatts) != 0) return false;
        if (Double.compare(that.rangeInMiles, rangeInMiles) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(powerKiloWatts);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rangeInMiles);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
