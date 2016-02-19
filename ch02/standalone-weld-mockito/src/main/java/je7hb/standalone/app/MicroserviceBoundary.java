package je7hb.standalone.app;

import javax.enterprise.context.RequestScoped;

/**
 * Created by ppilgrim on 19/02/2016.
 */
@RequestScoped
public class MicroserviceBoundary {

    public String getBoundaryName() {
        return "Embarcadero1000";
    }
}
