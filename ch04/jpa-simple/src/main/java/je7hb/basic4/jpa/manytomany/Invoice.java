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

package je7hb.basic4.jpa.manytomany;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Invoice
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Invoice implements java.io.Serializable {
    @Id
    @Column(name="INV_ID")
    private int id;

    @Column(name="INV_NAME", nullable = false)
    private String name;

    @Column(name="INV_DATE")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany(mappedBy="invoices", cascade = CascadeType.ALL )
    private Set<Product> products;

    public Invoice() {
        this(0, null);
    }

    public Invoice(int id, String name) {
        this(id, name, new Date(), new HashSet<Product>());
    }

    public Invoice(int id, String name, Date date, Set<Product> product) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.products = product;
    }

    public Set<Product> getProducts() {
        return products; }
    public void setProducts(Set<Product> products) {
        this.products = products; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        if (id != invoice.id) return false;
        if (date != null ? !date.equals(invoice.date) : invoice.date != null) return false;
        if (name != null ? !name.equals(invoice.name) : invoice.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", products=" + StringHelper.dumpList(products) +
                '}';
    }
}
