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

package je7hb.basic4.jpa.manytoone;

import javax.persistence.*;

/**
 * The type Employee2
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Employee2 {
    @Id
    @Column(name="EMP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;

    @Column(name="EMP_NUM",nullable = false)
    private int employeeNumber;
    private String firstName;
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Clearance clearance;

    public Employee2() {
        this(0,null,null, null );
    }

    public Employee2(int employeeNumber, String firstName, String lastNames, Clearance clearance) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.clearance = clearance;
        this.lastName = lastName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
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

    public Clearance getClearance() {
        return clearance;
    }

    public void setClearance(Clearance clearance) {
        this.clearance = clearance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee2)) return false;

        Employee2 employee2 = (Employee2) o;

        if (employeeId != employee2.employeeId) return false;
        if (employeeNumber != employee2.employeeNumber) return false;
        if (clearance != null ? !clearance.equals(employee2.clearance) : employee2.clearance != null) return false;
        if (firstName != null ? !firstName.equals(employee2.firstName) : employee2.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee2.lastName) : employee2.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId;
        result = 31 * result + employeeNumber;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (clearance != null ? clearance.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Employee2{" +
                "employeeId=" + employeeId +
                ", employeeNumber=" + employeeNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", clearance=" + clearance +
                '}';
    }
}
