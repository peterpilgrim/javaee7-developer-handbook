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
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type AbstractCdiContainerTest
 *
 * @author Peter Pilgrim
 */
public abstract class AbstractCdiContainerTest {

    protected static CdiContainer cdiContainer;
    private AtomicInteger containerRefCount = new AtomicInteger(0);

    @Before
    public final void setUp() throws Exception {
        System.out.printf("AbstractCdiContainerTest#setUp() containerRefCount=%d, cdiContainer=%s\n", containerRefCount.get(), cdiContainer );

        if ( cdiContainer != null ) {
            containerRefCount.incrementAndGet();

            final ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);

            //stop the RequestContext to dispose of the @RequestScoped EntityManager
            ctxCtrl.stopContext(RequestScoped.class);

            //immediately restart the context again
            ctxCtrl.startContext(RequestScoped.class);

            // perform injection into the very own test class
            final BeanManager beanManager = cdiContainer.getBeanManager();
            final CreationalContext creationalContext = beanManager.createCreationalContext(null);

            final AnnotatedType annotatedType = beanManager.createAnnotatedType(this.getClass());
            final InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
            injectionTarget.inject(this, creationalContext);
        }
    }

    @After
    public final void tearDown() throws Exception {
        System.out.printf("AbstractCdiContainerTest#tearDown() containerRefCount=%d, cdiContainer=%s\n", containerRefCount.get(), cdiContainer );
        if (cdiContainer != null) {
            final ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);

            //stop the RequestContext to dispose of the @RequestScoped EntityManager
            ctxCtrl.stopContext(RequestScoped.class);

            //immediately restart the context again
            ctxCtrl.startContext(RequestScoped.class);

//            cdiContainer.getContextControl().stopContext(RequestScoped.class);
//            cdiContainer.getContextControl().startContext(RequestScoped.class);
            containerRefCount.decrementAndGet();
        }
    }

    @BeforeClass
    public final synchronized static void startUpContainer() throws Exception {

        System.out.printf("AbstractCdiContainerTest#startUpContainer() cdiContainer=%s\n", cdiContainer );
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        cdiContainer.getContextControl().startContexts();
    }

    @AfterClass
    public final synchronized static void shutdownContainer() throws Exception {
        System.out.printf("AbstractCdiContainerTest#shutdownContainer() cdiContainer=%s\n", cdiContainer );
        if (cdiContainer != null) {
            cdiContainer.shutdown();
            cdiContainer = null;
        }
    }

    public void finalize() throws Throwable {
        System.out.printf("AbstractCdiContainerTest#finalize() cdiContainer=%s\n", cdiContainer );
        shutdownContainer();
        super.finalize();
    }
}
