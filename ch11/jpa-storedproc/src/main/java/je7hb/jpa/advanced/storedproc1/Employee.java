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

package je7hb.jpa.advanced.storedproc1;

import javax.persistence.*;

import static javax.persistence.ParameterMode.IN;

/**
 * The type Employee
 *
 * @author Peter Pilgrim (peter)
 */

@NamedStoredProcedureQuery(
    name = "Employee.findByRegion",
    procedureName = "EMP_READ_BY_REGION_SP",
    resultClasses = Employee.class,
    parameters = {
        @StoredProcedureParameter(mode=IN, name="REGION_ID", type=Integer.class),
    }
)
@Entity
@Table(name="EMPLOYEE")
@SecondaryTable(
    name="REGION",
    pkJoinColumns = {
        @PrimaryKeyJoinColumn(name = "REGION_ID")}
)
public class Employee {
    @Id @Column(name="EMPLOYEE_ID")
    String id;
    @Column(name="FIRST_NAME")
    String firstName;
    @Column(name="LAST_NAME")
    String lastName;
    @Column(name="NAME",table="REGION")
    String region;

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", region='" + region + '\'' +
            '}';
    }
}
