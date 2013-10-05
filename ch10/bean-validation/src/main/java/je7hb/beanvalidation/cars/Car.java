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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Car
 *
 * @author Peter Pilgrim
 */
public class Car {
    @NotNull(groups=BasicCheck.class)
    private final String carMaker;

    @Min(value=2, groups={BasicCheck.class,
            CompleteCheck.class})
    private final int seats;

    @Size(min=4, max=8, groups=BasicCheck.class)
    private final String licensePlate;

    @Min(value=500, groups={BasicCheck.class,
            CompleteCheck.class})
    private final int engineSize;

    public Car(String carMaker, int seats, String licensePlate) {
        this(carMaker, seats, licensePlate, 0 );
    }

    public Car(String carMaker, int seats, String licensePlate, int engineSize) {
        this.carMaker = carMaker;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.engineSize = engineSize;
    }

    public String getCarMaker() {
        return carMaker;
    }

    public int getSeats() {
        return seats;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getEngineSize() {
        return engineSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        if (engineSize != car.engineSize) return false;
        if (seats != car.seats) return false;
        if (carMaker != null ? !carMaker.equals(car.carMaker) : car.carMaker != null) return false;
        if (licensePlate != null ? !licensePlate.equals(car.licensePlate) : car.licensePlate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = carMaker != null ? carMaker.hashCode() : 0;
        result = 31 * result + seats;
        result = 31 * result + (licensePlate != null ? licensePlate.hashCode() : 0);
        result = 31 * result + engineSize;
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carMaker='" + carMaker + '\'' +
                ", seats=" + seats +
                ", licensePlate='" + licensePlate + '\'' +
                ", engineSize=" + engineSize +
                '}';
    }
}
