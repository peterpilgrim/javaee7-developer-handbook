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

package je7hb.jpa.advanced.criteria;

import org.junit.Before;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.Random;

/**
 * The type AbstractEmployeeCriteriaTest
 *
 * @author Peter Pilgrim
 */
public abstract class AbstractEmployeeCriteriaTest {
    @PersistenceContext
    protected EntityManager em;

    @Resource
    protected UserTransaction utx;

    private final static String[] FIRST_NAMES = {
            "Andrew", "Benjamin", "Brenda", "Diana", "Donald", "Doris",
            "Evan", "Eugene", "Fred", "Gale", "Gwen", "Indigo",
            "Jack", "Jacqueline", "James","Kim", "Kirk",
            "Mark", "Mary", "Neville", "Nora", "Oscar",
            "Peter", "Quentin", "Robert", "Sarah", "Sean",
            "Sue", "Tammy", "Thomas", "Timothy",
            "Wendy", "Xavier", "Zara"
    };

    private final static String[] LAST_NAMES = {
            "Adams", "Azure", "Botham", "Bunton", "Cae", "Calvin",
            "McAdam", "Divine", "Edgars", "Edmunton", "Edwards",
            "Franklin", "Gough", "Hill", "Jameson", "Kasabin", "King",
            "Murray", "Overton", "Peterson", "Pickford", "Rankin",
            "Richardson", "Ridge", "Silvers",
            "Thomson", "Watkin", "Zareen"
    };

    public final static int NUM_STAFF=14;
    public final static int NUM_DIRECTORS=3;

    @Before
    public void setupDatabase() throws Exception {
        utx.begin();
        Region defaultRegion = new Region(801, "Central");
        em.persist(defaultRegion);
        em.persist(new Region(802, "SE"));
        em.persist(new Region(803, "SW"));
        em.persist(new Region(804, "NE"));
        em.persist(new Region(805, "NW"));

        TaxCode defaultTaxCode = new TaxCode(501, "FTE");
        TaxCode directorTaxCode = new TaxCode(504, "Director");
        em.persist( defaultTaxCode );
        em.persist( new TaxCode(502, "Contractor"));
        em.persist( new TaxCode(503, "Temp"));
        em.persist( directorTaxCode);
        em.persist( new TaxCode(505, "OutSource"));

        Random rnd = new Random(System.currentTimeMillis());
        int empId=0;
        for (int j=0; j<NUM_STAFF; ++j ) {
            ++empId;
            String fn = FIRST_NAMES[rnd.nextInt(FIRST_NAMES.length)];
            String ln = LAST_NAMES[rnd.nextInt(LAST_NAMES.length)];
            Employee e = new Employee(empId, fn, ln );
            e.setSalary(new BigDecimal((rnd.nextInt(30) + 10) * 1000));
            em.persist(e);
        }
        for (int j=0; j<NUM_DIRECTORS; ++j ) {
            ++empId;
            String fn = FIRST_NAMES[rnd.nextInt(FIRST_NAMES.length)];
            String ln = LAST_NAMES[rnd.nextInt(LAST_NAMES.length)];
            Employee e = new Employee(empId, fn, ln, directorTaxCode, null );
            e.setSalary(new BigDecimal((rnd.nextInt(30) + 50) * 1000));
            em.persist(e);
        }
        utx.commit();
    }
}
