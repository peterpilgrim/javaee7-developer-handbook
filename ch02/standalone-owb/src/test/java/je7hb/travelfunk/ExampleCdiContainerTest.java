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

package je7hb.travelfunk;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.junit.Test;

/**
 * Verifies the operation of the TravelServiceTest
 *
 * @author Peter Pilgrim
 */
public class ExampleCdiContainerTest {


    @Test
    public void shouldWork() {
        // this will give you a CdiContainer for Weld or OWB, depending on the jar you added
        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();

    // now we gonna boot the CDI container. This will trigger the classpath scan, etc
        cdiContainer.boot();

        // and finally we like to start all built-in contexts
        cdiContainer.getContextControl().startContexts();

        // No specific JBoss Weld or Open Web Beans included in the test code

        // finally we gonna stop the container
        cdiContainer.shutdown();
    }
}
