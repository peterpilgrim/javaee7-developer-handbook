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

import je7hb.basic.ejb.examples.DataRepository;
import je7hb.basic.ejb.examples.EnrichmentManager;
import je7hb.basic.ejb.examples.MatchingEngine;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The type PostTradeProcessor
 *
 * @author Peter Pilgrim
 */
@Stateless
@LocalBean
public class PostTradeProcessor {

    @EJB DataRepository dataRepository;
    @EJB EnrichmentManager enrichmentManager;
    @Inject MatchingEngine matchingEngine;

    public void preProcess( Product product ) { /* ... */ }

    public void process( Product product ) { /* ... */ }

    public void postProcess( Product product ) { /* ... */ }
}
