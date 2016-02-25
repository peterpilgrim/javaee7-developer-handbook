package je7hb.weld.helloworld;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import java.util.List;


/**
 DDD* Example application with JBoss WELD SE
 *
 * @author peterpilgrim
 */
@Singleton
public class HelloWorld {
    public void initialMe(
            @Observes ContainerInitialized event,
            @Parameters List<String> parameters) {
        System.out.println("Initialization from CDI");
        for (int j = 0; j < parameters.size(); ++j) {
            final String param = parameters.get(j);
            System.out.printf(
                    "  parameters[%d] = %s\n", j, param);
        }
        System.out.println("Complete.");
    }

    public void greet(String[] names) {
        System.out.print("Hello ");
        for (int j = 0; j < names.length; ++j) {
            System.out.printf("%s%s",
                    (j > 0 ? (j == names.length - 1 ?
                            " and " : ", ") : ""),
                    names[j]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        final Weld weld = new Weld();
        final WeldContainer container = weld.initialize();
        final HelloWorld helloBean = container.instance()
                .select(HelloWorld.class).get();
        helloBean.greet(args);

        weld.shutdown();
    }
}
