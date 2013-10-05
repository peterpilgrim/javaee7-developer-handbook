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

package je7hb.basic.singleton;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * The type ConductorMasterBean
 *
 * @author Peter Pilgrim (peter)
 */
@Startup
@Singleton(name="ConductorMasterBean")
// @EJB(name="java:app/ConductorMasterRemote", beanInterface=ConductorMasterRemote.class)
// @LocalBean
public class ConductorMasterBean implements ConductorMasterRemote {

    private Properties properties = new Properties();

    @Override
    public String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown-host";
        }
    }

    @Override
    public int getPort() {
        return 32678;
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @PostConstruct
    public void appStartUp() {
        System.out.printf("ConductorMasterBean#appStartUp() %s\n", this);
        properties.putAll(System.getProperties());
    }

    @PreDestroy
    public void appShutdown() {
        System.out.printf("ConductorMasterBean#appShutdown() %s\n", this);
        properties.clear();
    }
}
