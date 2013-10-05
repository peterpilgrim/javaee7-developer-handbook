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

package je7hb.basic5.jpa;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The type Payroll
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
@Table(name="PAYROLL_EZ",
        schema="HUMAN_RESOURCES",
        catalog="EUROPE"
        /* JPA 2.1
        ,
        uniqueConstraints=@Unique(
                columnNames="PAYROLL_PRINT_SER"),
        indexes = {
        @Index(name="index1",
                columnNames={"firstName", "lastName"} )
            ) */
        )
public class Payroll implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long payrollId;
    String firstName;
    String lastName;
    @Column(name="PAYROLL_PRINT_SER")
    String payrollSerial;

    public Payroll() {
        this( null, null, null );
    }

    public Payroll(String firstName, String lastName, String payrollSerial) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.payrollSerial = payrollSerial;
    }

    public long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(long payrollId) {
        this.payrollId = payrollId;
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

    public String getPayrollSerial() {
        return payrollSerial;
    }

    public void setPayrollSerial(String payrollSerial) {
        this.payrollSerial = payrollSerial;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", payrollSerial='" + payrollSerial + '\'' +
                '}';
    }
}

