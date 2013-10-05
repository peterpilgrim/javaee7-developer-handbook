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

package je7hb.basic4.jpa.manytomany;

import java.util.Collection;

/**
 * The type StringHelper
 *
 * @author Peter Pilgrim
 */
public final class StringHelper {
    private StringHelper() {
    }

    public static <T> String dumpList( Collection<T> list) {
        StringBuilder buf = new StringBuilder();
        if ( list == null) {
            buf.append("null");
        }
        else {
            buf.append('[');
            int count = 0;
            for ( T elem: list ) {
                if ( count > 0 ) buf.append(',');
                if ( elem == null) {
                    buf.append("null");
                }
                else {
                    buf.append(elem.getClass().getSimpleName()+'@');
                    buf.append(Integer.toHexString(System.identityHashCode(elem)));
                }
                count++;
            }
            buf.append(']');
        }
        return buf.toString();
    }

}
