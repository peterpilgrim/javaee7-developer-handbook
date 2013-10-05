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

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.*;

/**
 * The type SupportHelpDesk
 *
 * @author Peter Pilgrim (peter)
 */
@Stateless
public class SupportHelpDesk {

    private List<String> agents = Arrays.asList(
            "Agnes","Brian","Harry","Sally","Tom","Pamela",
            "Mark","Wendy","Marcia","Graeme","Pravztik",
            "Hadeep", "Florence", "Robert", "Zoe", "Frank" );

    public String getNextAgentName() {
        return agents.get((int)( Math.random() * agents.size() ));
    }
}
