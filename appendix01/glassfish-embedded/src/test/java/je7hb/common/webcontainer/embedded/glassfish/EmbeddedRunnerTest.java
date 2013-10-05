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

package je7hb.common.webcontainer.embedded.glassfish;

import static org.junit.Assert.*;
import org.junit.*;

public class EmbeddedRunnerTest {

//    public static final String TEST_WAR_FILE = "build/libs/glassfish-embedded-runner-1.0.war";
    public static final String TEST_WAR_FILE = "glassfish-embedded-runner-1.0.war";
    public static final String TEST_WEB_CONTEXT = "test1234";

    @Test
	public void shouldDeployEmbeddedServer() throws Exception {
        System.out.println("We need to deploy a test server");

        SimpleEmbeddedRunner runner = (SimpleEmbeddedRunner)new SimpleEmbeddedRunner(8080);

        System.out.println("**** INITIALIZING SERVER ****");
        assertFalse(runner.isInitialized());
        runner.init();
        assertTrue(runner.isInitialized());

        System.out.println("**** STARTING SERVER ****");
        assertFalse(runner.isRunning());
        runner.start();
        assertTrue(runner.isRunning());

        System.out.printf("deployments=%s\n", runner.getDeployments() );
        assertFalse( runner.getDeployments().contains(TEST_WEB_CONTEXT));

        System.out.println("**** DEPLOYING WEB APP ****");
        runner.deployWithRename(TEST_WAR_FILE, TEST_WEB_CONTEXT);
        System.out.printf("deployments=%s\n", runner.getDeployments() );
        assertTrue( runner.getDeployments().contains(TEST_WEB_CONTEXT));

        Thread.sleep(1000);
        assertTrue(runner.isRunning());
        assertTrue(runner.isInitialized());

        System.out.println("**** UNDEPLOYING WEB APP ****");
        System.out.printf("deployments=%s\n", runner.getDeployments() );
        runner.undeploy(TEST_WEB_CONTEXT);
        assertFalse( runner.getDeployments().contains(TEST_WEB_CONTEXT));
        System.out.printf("deployments=%s\n", runner.getDeployments() );

        System.out.println("**** STOPPING SERVER ****");
        runner.stop();

        assertFalse(runner.isRunning());
        assertTrue(runner.isInitialized());

        Thread.sleep(1000);

        System.out.println("**** DONE ****");
	}
} 	

