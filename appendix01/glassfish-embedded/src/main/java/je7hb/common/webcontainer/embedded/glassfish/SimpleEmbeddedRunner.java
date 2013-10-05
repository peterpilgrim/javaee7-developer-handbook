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

import java.io.File;
import java.util.Scanner;

/**
 * The type SimpleEmbeddedRunner
 *
 * @author Peter Pilgrim (peter)
 */
public class SimpleEmbeddedRunner extends AbstractEmbeddedRunner {

    /**
     * Default embedded server port number
     */
    public final static int DEFAULT_PORT=8080;

    /**
     * Default pause time in milliseconds after deploying the war file
     */
    public static final long DEFAULT_PAUSE_TIME = 1000L;

    public SimpleEmbeddedRunner(int port) {
        super(port);
    }

    public static void launchDeployWarFileAndWait( String warFile, String webContext ) throws Exception {
        launchDeployWarFileAndWait(warFile, webContext, DEFAULT_PORT );
    }

    public static void launchDeployWarFileAndWait( String warFile, String webContext, int port ) throws Exception {
        launchDeployWarFileAndWait(new File(warFile), webContext, port, DEFAULT_PAUSE_TIME);
    }

    public static void launchDeployWarFileAndWait( String warFile, String webContext, int port, long milliseconds ) throws Exception {
        launchDeployWarFileAndWait(new File(warFile), webContext, port, milliseconds);
    }

    public static void launchDeployWarFileAndWait( File warFile, String webContext ) throws Exception {
        launchDeployWarFileAndWait(warFile, webContext, DEFAULT_PORT );
    }

    public static void launchDeployWarFileAndWait( File warFile, String webContext, int port ) throws Exception {
        launchDeployWarFileAndWait(warFile, webContext, port, DEFAULT_PAUSE_TIME);
    }

    public static void launchDeployWarFileAndWait( File warFile, String webContext, int port, long milliseconds ) throws Exception {
        SimpleEmbeddedRunner runner = (SimpleEmbeddedRunner)new SimpleEmbeddedRunner(port).init().start();
        runner.deployWithRename(warFile, webContext);
        Thread.sleep(milliseconds);
        System.out.printf("**** Press the ENTER key to stop the server ****");
        Scanner sc = new Scanner(System.in);
        while(!sc.nextLine().equals(""));
        runner.stop();
    }

    public static SimpleEmbeddedRunner launchDeployWarFile( File warFile, String webContext, int port ) throws Exception {
        SimpleEmbeddedRunner runner = (SimpleEmbeddedRunner)new SimpleEmbeddedRunner(port).init().start();
        runner.deployWithRename(warFile, webContext);
        return runner;
    }
}
