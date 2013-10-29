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

package je7hb.standalone.events;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * The type ApprovalNotifier
 *
 * @author Peter Pilgrim
 */
public class ApprovalNotifier {
    @Inject Event<ApplicationApproved> eventSource;

    public void fireEvents( String msg ) {
        eventSource.fire(new ApplicationApproved(msg));
    }

    @Inject @LongTerm
    Event<ApplicationApproved> longTermEventSource;

    public void fireLongTermEvents( String msg ) {
        longTermEventSource.fire(new ApplicationApproved(msg));
    }
}
