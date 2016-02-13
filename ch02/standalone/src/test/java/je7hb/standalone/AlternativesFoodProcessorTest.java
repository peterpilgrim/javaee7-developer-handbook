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

import static org.junit.Assert.*;

import je7hb.standalone.alternatives.FoodProcessor;
import je7hb.travelfunk.AbstractCdiContainerTest;
import org.junit.*;

import javax.inject.Inject;

/**
 * A unit test AlternativesFoodProcessorTest to verify the operation of AlternativesFoodProcessorTest
 *
 * @author Peter Pilgrim
 */
public class AlternativesFoodProcessorTest extends AbstractCdiContainerTest {

    private @Inject FoodProcessor foodProcessor;

    @Test
    public void shouldInjectAlternative() {
        System.out.printf("java.class.path=%s\n", System.getProperty("java.class.path"));
        System.out.printf("java.home=%s\n", System.getProperty("java.home"));
        System.out.printf("user.dir=%s\n", System.getProperty("user.dir"));
        assertNotNull(foodProcessor);
        assertEquals("Xenonique", foodProcessor.sayBrand());
    }
}
