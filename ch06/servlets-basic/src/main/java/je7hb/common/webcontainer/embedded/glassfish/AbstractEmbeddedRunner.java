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

import org.glassfish.embeddable.*;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type AbstractEmbeddedRunner
 *
 * @author Peter Pilgrim
 */
public class AbstractEmbeddedRunner {

    private int port;
    private AtomicBoolean initialized = new AtomicBoolean();
    private GlassFish glassfish;

    public AbstractEmbeddedRunner(int port) {
        this.port = port;
    }

    public AbstractEmbeddedRunner init() throws Exception{
        if ( initialized.get() ) {
            throw new RuntimeException("runner was already initialized");
        }

        BootstrapProperties bootstrapProperties = new BootstrapProperties();
        GlassFishRuntime glassfishRuntime = GlassFishRuntime.bootstrap(bootstrapProperties);

        GlassFishProperties glassfishProperties = new GlassFishProperties();
        glassfishProperties.setPort("http-listener", port);
//        glassfishProperties.setPort("https-listener", port+1);
        String [] paths = System.getProperty("java.class.path").split(File.pathSeparator);
        for (int j=0; j<paths.length; ++j) {
              System.out.printf("classpath[%d] = %s\n", j, paths[j]);
        }

        glassfish = glassfishRuntime.newGlassFish(glassfishProperties);
        initialized.set(true);
        return this;
    }

    private void check() {
        if ( !initialized.get() ) {
            throw new RuntimeException("runner was not initialised");
        }
    }

    public AbstractEmbeddedRunner start() throws Exception{
        check();
        glassfish.start();
        return this;
    }

    public AbstractEmbeddedRunner stop() throws Exception{
        check();
        glassfish.stop();
        return this;
    }

    public AbstractEmbeddedRunner deploy( String args[]) throws Exception{
        Deployer deployer = glassfish.getDeployer();
        for (String s: args) {
            String application = deployer.deploy(new File(s));
            System.out.printf("deploying "+application);
        }
        return this;
    }

    public AbstractEmbeddedRunner deployWithRename( String war, String newContext ) throws Exception{
        Deployer deployer = glassfish.getDeployer();
        deployer.deploy(new File(war), "--name="+newContext, "--contextroot="+newContext, "--force=true");
        return this;
    }

    public AbstractEmbeddedRunner undeploy() throws Exception {
        Deployer deployer = glassfish.getDeployer();
        for ( String s: deployer.getDeployedApplications()) {
            deployer.undeploy(s);
        }
        return this;
    }


}
