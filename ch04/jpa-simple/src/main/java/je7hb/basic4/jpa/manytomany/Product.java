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
import java.util.HashSet;
import java.util.Set;

/**
 * The type Product
 *
 * @author Peter Pilgrim (peter)
 */
@Entity
public class Product implements java.io.Serializable {
    @Id
    @Column(name="PROD_ID")
    private int id;
    @Column(name="PROD_NAME")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    public Product() {
        this(0, null);
    }

    public Product(int id, String name) {
        this( id, name, new HashSet<Invoice>());
    }

    public Product(int id, String name, Set<Invoice> invoices) {
        this.id = id;
        this.name = name;
        this.invoices = invoices;
    }

    public Set<Invoice> getInvoices() {
        return invoices; }
    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", invoices=" + StringHelper.dumpList(invoices) +
                '}';
    }

}
