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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type AbstractEmbeddedRunner
 *
 * @author Peter Pilgrim
 */
public class AbstractEmbeddedRunner {

    private int port;
    private AtomicBoolean initialized = new AtomicBoolean();
    private AtomicBoolean running = new AtomicBoolean();
    private GlassFish glassfish;
    private GlassFishRuntime glassfishRuntime;

    public AbstractEmbeddedRunner(int port) {
        this.port = port;
    }

    public AbstractEmbeddedRunner init() throws Exception{
        if ( initialized.get() ) {
            throw new RuntimeException("runner was already initialized");
        }

        BootstrapProperties bootstrapProperties = new BootstrapProperties();
        glassfishRuntime = GlassFishRuntime.bootstrap(bootstrapProperties);

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
        running.set(true);
        return this;
    }

    public AbstractEmbeddedRunner stop() throws Exception{
        check();
        try {
            // this will stop and dispose all the glassfish instances created with this gfr
            // if you were to bootstrap GlassFishRuntime again, Shutdown GlassFish.
            // this will avoid "already bootstrapped" errors seen when running multiple tests
            // in same VM
            undeployAll();
            glassfish.stop();
            glassfish.dispose();
            glassfishRuntime.shutdown();
            running.set(false);
        } catch (GlassFishException ex) {
            throw new RuntimeException("Failed to shutdown Embedded GlassFish container", ex);
        }
        return this;
    }

    public AbstractEmbeddedRunner deploy( String args[]) throws Exception{
        Deployer deployer = glassfish.getDeployer();
        for (String s: args) {
            File f = new File(s);
            sanityCheckFile(f);
            String application = deployer.deploy(f);
            System.out.printf("deploying "+application);
        }
        return this;
    }

    /**
     * Deploy the WAR file and also override the web context name
     * @param warfile the war file path
     * @param newContext  the web context name
     * @return embedded runner
     * @throws Exception
     */
    public AbstractEmbeddedRunner deployWithRename( String warfile, String newContext ) throws Exception{
        return deployWithRename( new File(warfile), newContext );
    }

    /**
     * Deploy the WAR file and also override the web context name
     * @param warfile the war file
     * @param newContext  the web context name
     * @return embedded runner
     * @throws Exception
     */
    public AbstractEmbeddedRunner deployWithRename( File warfile, String newContext ) throws Exception{
        Deployer deployer = glassfish.getDeployer();
        sanityCheckFile(warfile);
        deployer.deploy(warfile, "--name="+newContext, "--contextroot="+newContext, "--force=true");
        return this;
    }

    private void sanityCheckFile(File f) throws IOException {
        if ( !f.exists() ) {
            throw new FileNotFoundException("The WAR file: ["+f.getPath()+"] does not exist.");
        }
        if ( !f.canRead() ) {
            throw new IOException("The WAR file: ["+f.getPath()+"] is not readable.");
        }
        if ( !f.isFile() ) {
            throw new IOException("The WAR file: ["+f.getPath()+"] is a regular file.");
        }
    }

    public AbstractEmbeddedRunner undeploy( String webContextName ) throws Exception {
        Deployer deployer = glassfish.getDeployer();
        Collection<String> deployedApplications = deployer.getDeployedApplications();
        if ( deployedApplications.contains(webContextName)) {
            deployer.undeploy(webContextName);
        }
        return this;
    }

    public AbstractEmbeddedRunner undeployAll() throws Exception {
        Deployer deployer = glassfish.getDeployer();
        for ( String s: deployer.getDeployedApplications()) {
            deployer.undeploy(s);
        }
        return this;
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean isInitialized() {
        return initialized.get();
    }

    public List<String> getDeployments() throws GlassFishException {
        List<String> deployments = new ArrayList<>();
        Deployer deployer = glassfish.getDeployer();
        for ( String s: deployer.getDeployedApplications()) {
            deployments.add(s);
        }
        return deployments;
    }
}
