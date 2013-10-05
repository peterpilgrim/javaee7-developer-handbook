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

package je7hb.basic.ejb;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

/**
 * The type ShoppingCartBean
 *
 * @author Peter Pilgrim
 */
@Stateful
public class ShoppingCartBean implements ShoppingCart, ShoppingCartLocal {
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private Customer customer = null;
    private boolean initialised = false;

    @Override
    public void initialize(Customer customer)  {
        System.out.printf("ShoppingCartBean#initialize() called %s\n", this );
        this.customer = customer;
        initialised = true;
    }

    protected void check() {
        if ( !initialised )  {
            throw new RuntimeException(
                    "This shopping cart is not initialised");
        }
    }

    @Override
    public void addOrderItem(OrderItem item) {
        System.out.printf("ShoppingCartBean#addOrderItem() called %s\n", this );
        check();
        orderItems.add( item );
    }

    @Override
    public void removeOrderItem(OrderItem item) {
        System.out.printf("ShoppingCartBean#removeOrderItem() called %s\n", this );
        check();
        orderItems.remove( item );
    }

    @Override
    public List<OrderItem> getOrderItems() {
        check();
        return orderItems;
    }

    @Remove
    public void release() {
        System.out.printf("ShoppingCartBean#release() called %s\n", this );
        orderItems.clear();
        customer = null;
    }
}
