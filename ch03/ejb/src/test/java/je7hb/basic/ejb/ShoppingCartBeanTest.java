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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type ShoppingCartBeanTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ShoppingCartBeanTest {
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Customer.class,OrderItem.class,
                        Product.class,ShoppingCartLocal.class,
                        ShoppingCart.class,
                        ShoppingCartBean.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        return jar;
    }

    Product p1 = new Product(
            1000, "IWG", "Iron Widget Grayson",
            new BigDecimal("4.99" ));
    Product p2 = new Product(
            1002, "MSB", "Miller Steel Bolt",
            new BigDecimal("8.99" ));
    Product p3 = new Product(
            1004, "ASCC", "Alphason Carbonite",
            new BigDecimal("15.99" ));

    Customer customer = new Customer("Fred","Other");

    @EJB
    private ShoppingCartLocal cart;

    private void dumpCart( List<OrderItem> items ) {
        BigDecimal total = BigDecimal.ZERO;
        System.out.println("========================");
        System.out.printf("%3s %8s %20s %8s %8s\n",
                "Qty","Name","Description","UP","QtyP");
        for ( OrderItem item: items )  {
            BigDecimal productPrice =
                    item.getProduct().getPrice().multiply(
                    new BigDecimal(item.getQuantity() ));
            Product product = item.getProduct();
            System.out.printf("%3d %8s %20s %8.2f %8.2f\n",
                    item.getQuantity(), product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    productPrice );

            total = total.add(productPrice);
        }

        System.out.printf("Total Price = %8.2f\n", total);
        System.out.println("========================\n");
    }

    @Test
    public void shouldAddItemsToCart() {
        System.out.printf("cart = %s\n", cart);
        assertNotNull(cart);

        cart.initialize(customer);

        System.out.printf("Initial state of the cart\n");
        dumpCart( cart.getOrderItems());

        OrderItem item1 = new OrderItem( 4, p1 );
        cart.addOrderItem( item1 );
        assertEquals( 1, cart.getOrderItems().size() );

        System.out.printf("After adding one item\n");
        dumpCart( cart.getOrderItems());

        OrderItem item2 = new OrderItem( 7, p2 );
        cart.addOrderItem( item2 );
        assertEquals(2, cart.getOrderItems().size());

        System.out.printf("After adding two items");
        dumpCart( cart.getOrderItems());

        OrderItem item3 = new OrderItem( 10, p3 );
        cart.addOrderItem( item3 );
        assertEquals(3, cart.getOrderItems().size());

        System.out.printf("After adding three items\n");
        dumpCart( cart.getOrderItems());
//        System.out.printf("cart.orderItems=%s\n", cart.getOrderItems()) ;

        cart.release();
    }
}
