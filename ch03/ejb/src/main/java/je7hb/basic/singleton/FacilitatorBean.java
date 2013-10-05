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
import javax.ejb.EJB;
import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.ejb.Singleton;

/**
 * The type FacilitatorBean
 *
 * @author Peter Pilgrim (peter)
 */
@Startup
@Singleton
// @DependsOn("ConductorMasterBean")
public class FacilitatorBean implements FacilitatorRemote {

//    @Resource SessionContext ctx;

    // http://glassfish.java.net/javaee5/ejb/EJB_FAQ.html#Are_Global_JNDI_names_portable_How_do

//    @EJB(mappedName = "java:app/ConductorMasterRemote")
    @EJB(name = "ConductorMasterBean") ConductorMasterRemote conductor;

    @Override
    public ConductorMasterRemote getConductorMaster() {
//        final String BEAN_NAME = "java:global/ConductorMasterRemote";
//        ConductorMasterRemote conductor = null;
//        try {
//            conductor = (ConductorMasterRemote)new InitialContext().lookup(BEAN_NAME);
//        } catch (Throwable t) {
//      //      t.printStackTrace(System.err);
//            throw new RuntimeException("unable to lookup bean by JNDI ("+BEAN_NAME+")",t);
//        }
        return conductor;
    }

    @PostConstruct
    public void appStartUp() {
        System.out.printf("FacilitatorBean#appStartUp() %s\n", this);
    }

    @PreDestroy
    public void appShutdown() {
        System.out.printf("FacilitatorBean#appShutdown() %s\n", this);
    }
}
