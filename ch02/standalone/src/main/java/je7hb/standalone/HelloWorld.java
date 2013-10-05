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

package je7hb.standalone;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class HelloWorld
{
    public void initialMe(
            @Observes ContainerInitialized event,
            @Parameters List<String> parameters) {
        System.out.println("Initialization from CDI");
        for ( int j=0; j<parameters.size(); ++j ) {
            String param = parameters.get(j);
            System.out.printf(
                "  parameters[%d] = %s\n", j, param );
        }
        System.out.println("Complete.");
    }

    public void greet( String[] names) {
        System.out.print("Hello ");
        for ( int j=0; j<names.length; ++j ) {
            System.out.printf("%s%s",
                    (j > 0 ? ( j == names.length-1 ?
                            " and " : ", ") : ""),
                    names[j]);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        HelloWorld helloBean = container.instance()
            .select(HelloWorld.class).get();
        helloBean.greet(args);

        weld.shutdown();
    }
}

