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

package je7hb.standalone;

import je7hb.standalone.alternatives.FoodProcessor;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A unit test AlternativesFoodProcessorTest to verify the operation of AlternativesFoodProcessor.
 * This test directly uses the Weld SE container API instead of Delta Spike
 *
 * @author Peter Pilgrim
 */
public class AlternativesFoodProcessorWeldSETest {

    private FoodProcessor foodProcessor;

    @Test
    public void shouldInjectAlternative() {

        System.out.printf("java.class.path=%s\n", System.getProperty("java.class.path"));
        System.out.printf("java.home=%s\n", System.getProperty("java.home"));
        System.out.printf("user.dir=%s\n", System.getProperty("user.dir"));

        Weld weld = new Weld();

        WeldContainer container = weld.initialize();

        foodProcessor = container.select(FoodProcessor.class).get();

        assertNotNull(foodProcessor);
        assertEquals("Xenonique", foodProcessor.sayBrand());

        container.shutdown();
    }


}
