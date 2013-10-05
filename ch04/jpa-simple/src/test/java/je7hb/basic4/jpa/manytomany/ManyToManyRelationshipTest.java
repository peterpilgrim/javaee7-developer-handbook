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

import je7hb.Utils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import je7hb.Utils;

/**
 * A unit test OneToOneRelationshipTest to verify the operation of OneToOneRelationshipTest
 *
 * <p> JPA Notes: Modify both sides of the relationship </p
 *
 * <ol>
 * <li>
 * If you have a bi-directional ManyToMany relationship,
 * ensure that you add to both sides of the relationship.
 * </li>
 *
 * <li> If you have a bidirectional ManyToMany relationship,
 * you must use mappedBy on one side of the relationship,
 * otherwise it will be assumed to be two difference relationships
 * and you will get duplicate rows inserted into the join table.
 *  </li>
 *  </ol>
 *
 * <p>Here we validate the many to many relationship by breaking it
 * into two relationships <em>one-to-many</em> and <em>many-to-one</em>.
 * </p>
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class ManyToManyRelationshipTest {

    private List<Product> products;
    private List<Invoice> invoices;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(Product.class)
            .addClasses(Invoice.class)
            .addAsResource(
                    "test-persistence.xml",
                    "META-INF/persistence.xml")
            .addAsManifestResource(
                    EmptyAsset.INSTANCE,
                    ArchivePaths.create("beans.xml"));
        return jar;
    }

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    @Before
    public void cleanDatabase() throws Exception {
        utx.begin();
        assertNotNull(em);
        em.joinTransaction();
        em.createQuery("delete from "+Product.class.getSimpleName()).executeUpdate();
        em.createQuery("delete from "+Invoice.class.getSimpleName()).executeUpdate();
        utx.commit();
    }

    @After
    public void clearEntityManager() {
        Utils.clearEntityManager(em);
    }

    private void createEntities() {

        products = Arrays.asList(
                new Product(100,"Forged Nuts"),
                new Product(101,"Steel Spirals"),
                new Product(102,"Bolt Fasteners"),
                new Product(103,"Ridge Drill"),
                new Product(104,"Mason Hammer")
        );

        invoices = Arrays.asList(
                new Invoice(1,"Appleton"),
                new Invoice(2,"Falvestron"),
                new Invoice(3,"Johnstone"),
                new Invoice(4,"Pemberton")
        );
    }

    public static <A,B> void assertEqualsOnCollectionsAnyOrder( Collection<A> source, Collection<B> target ) {
        assertEquals("collection sizes are different", source.size(), target.size());
        for (Object obj:source) {
            if (!target.contains(obj)) {
                fail("collections are different target does not contain source object: " + obj);
            }
        }
    }


    @Test
    public void shouldPersistProductsAndInvoicesWithoutLinks() throws Exception {
        // Assign
        createEntities();

        // Act
        utx.begin();
        for ( Product prod: products) {
            em.persist(prod);
        }
        for ( Invoice inv: invoices) {
            em.persist(inv);
        }
        utx.commit();

        System.out.printf("products = %s\n", products );
        System.out.printf("invoices = %s\n", invoices );

        // Asserts
        for ( Product prod: products) {
            assertTrue( prod.getId() > 0 );
        }

        // Asserts
        for ( Invoice inv: invoices) {
            assertTrue( inv.getId() > 0 );
        }
    }

    @Test
    public void shouldPersistOneProductWithManyInvoices() throws Exception {
        // Assign
        createEntities();

        Product expectedProduct = products.get(1);
        List<Invoice> expectedInvoices = Arrays.asList(invoices.get(0), invoices.get(2));
        expectedProduct.getInvoices().add( invoices.get(0));
        expectedProduct.getInvoices().add( invoices.get(2));
        invoices.get(0).getProducts().add( expectedProduct);
        invoices.get(2).getProducts().add( expectedProduct);

        // Act
        utx.begin();
        for ( Product prod: products) {
            em.persist(prod);
        }
        for ( Invoice inv: invoices) {
            em.persist(inv);
        }
        utx.commit();

        System.out.printf("products = %s\n", products );
        System.out.printf("invoices = %s\n", invoices );

        utx.begin();
        System.out.printf("expectedProduct = %s\n", expectedProduct);
        List<Invoice> invoicesWithProductResult =
                em.createQuery(
                        "SELECT DISTINCT i " +
                                "FROM Invoice i JOIN i.products p " +
                                "WHERE p.id = :productId  ")
                        .setParameter("productId", expectedProduct.getId())
                        .getResultList();
        List<Product> productWithInvoicesResult =
                em.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM Product p JOIN p.invoices i " +
                                "WHERE p.id = :productId  ")
                        .setParameter("productId", expectedProduct.getId())
                        .getResultList();
        utx.commit();
        System.out.printf("**** invoicesWithProductResult = %s\n", invoicesWithProductResult);
        System.out.printf("**** productWithInvoicesResult = %s\n", productWithInvoicesResult );

        // Asserts
        for ( Product prod: products) {
            assertTrue( prod.getId() > 0 );
        }

        // Asserts
        for ( Invoice inv: invoices) {
            assertTrue( inv.getId() > 0 );
        }

        assertEquals(2, invoicesWithProductResult.size());
        for ( Invoice inv: invoicesWithProductResult ) {
            System.out.printf("invoicesWithProductResult = %s\n", inv );
            int count = 0;
            assertTrue(inv.getProducts().contains(expectedProduct));
            for ( Product p: inv.getProducts() )  {
                System.out.printf("    .product[%d] = %s\n", count, p );
                assertTrue( "Broken relationship invoice -> products -> invoice",
                        p.getInvoices().contains(inv) );
                ++count;
            }
        }

        assertEquals( 1, productWithInvoicesResult.size());
        for ( Product prod: productWithInvoicesResult ) {
            System.out.printf("productWithInvoicesResult = %s\n", prod );
            int count = 0;
            assertEqualsOnCollectionsAnyOrder(expectedInvoices, prod.getInvoices());
            for ( Invoice inv: prod.getInvoices() )  {
                System.out.printf("    .invoices[%d] = %s\n", count, inv );
                assertTrue( "Broken inverse relationship product -> invoices -> product",
                        inv.getProducts().contains(prod) );
                ++count;
            }
        }
    }

    @Test
    public void shouldPersistManyProductWithOneInvoices() throws Exception {
        // Assign
        createEntities();

        Invoice expectedInvoice = invoices.get(3);
        List<Product> expectedProducts = Arrays.asList(products.get(0), products.get(1), products.get(3));

        expectedInvoice.getProducts().add( products.get(0));
        expectedInvoice.getProducts().add( products.get(1));
        expectedInvoice.getProducts().add( products.get(3));
        products.get(0).getInvoices().add( expectedInvoice);
        products.get(1).getInvoices().add( expectedInvoice);
        products.get(3).getInvoices().add( expectedInvoice);

        // Act
        utx.begin();
        for ( Product prod: products) {
            em.persist(prod);
        }
        for ( Invoice inv: invoices) {
            em.persist(inv);
        }
        utx.commit();

        System.out.printf("products = %s\n", products );
        System.out.printf("invoices = %s\n", invoices );
                     // + 44(0) 1732 462801
        utx.begin();
        System.out.printf("expectedInvoice = %s\n", expectedInvoice);
        List<Invoice> invoicesWithProductResult =
                em.createQuery(
                        "SELECT DISTINCT i " +
                                "FROM Invoice i JOIN i.products p " +
                                "WHERE i.id = :invoiceId  ")
                        .setParameter("invoiceId", expectedInvoice.getId())
                        .getResultList();
        List<Product> productWithInvoicesResult =
                em.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM Product p JOIN p.invoices i " +
                                "WHERE i.id = :invoiceId  ")
                        .setParameter("invoiceId", expectedInvoice.getId())
                        .getResultList();
        utx.commit();
        System.out.printf("**** invoicesWithProductResult = %s\n", invoicesWithProductResult);
        System.out.printf("**** productWithInvoicesResult = %s\n", productWithInvoicesResult );

        // Asserts
        for ( Product prod: products) {
            assertTrue( prod.getId() > 0 );
        }

        // Asserts
        for ( Invoice inv: invoices) {
            assertTrue( inv.getId() > 0 );
        }

        assertEquals(1, invoicesWithProductResult.size());
        for ( Invoice inv: invoicesWithProductResult ) {
            System.out.printf("invoicesWithProductResult = %s\n", inv );
            int count = 0;
            assertEqualsOnCollectionsAnyOrder(expectedProducts, inv.getProducts());
            for ( Product p: inv.getProducts() )  {
                System.out.printf("    .product[%d] = %s\n", count, p );
                assertTrue( "Broken relationship invoice -> products -> invoice",
                        p.getInvoices().contains(inv) );
                ++count;
            }
        }

        assertEquals( 3, productWithInvoicesResult.size());
        for ( Product prod: productWithInvoicesResult ) {
            System.out.printf("productWithInvoicesResult = %s\n", prod );
            int count = 0;
            assertTrue(prod.getInvoices().contains(expectedInvoice));
            for ( Invoice inv: prod.getInvoices() )  {
                System.out.printf("    .invoices[%d] = %s\n", count, inv );
                assertTrue( "Broken inverse relationship product -> invoices -> product",
                        inv.getProducts().contains(prod) );
                ++count;
            }
        }
    }


}
