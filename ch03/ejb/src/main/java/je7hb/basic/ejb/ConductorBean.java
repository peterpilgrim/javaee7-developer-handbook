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

package je7hb.basic.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.util.Properties;

/**
 * The type ConductorBean
 *
 * @author Peter Pilgrim
 */
@Singleton
public class ConductorBean implements Conductor {
    private final Properties properties = new Properties();

    @Override
    public void orchestrate(String data) {
        System.out.printf("ConductorBean#orchestrate( %s ) %s\n", data, this );
    }

    @PostConstruct
    public void appStartUp() {
        properties.putAll(System.getProperties());
        System.out.printf("ConductorBean#appStartUp() %s\n" +
                "java.version=%s\n", this, properties.get("java.version") );
    }

    @PreDestroy
    public void appShutDown() {
        System.out.printf("ConductorBean#appShutDown() %s\n", this );
        properties.clear();
    }
}


