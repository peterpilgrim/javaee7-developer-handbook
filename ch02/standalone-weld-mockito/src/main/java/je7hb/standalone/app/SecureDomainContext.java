package je7hb.standalone.app;

import javax.enterprise.context.RequestScoped;

/**
 * Created by ppilgrim on 19/02/2016.
 */
@RequestScoped
public class SecureDomainContext {

    public void authenticate(String username, byte[] encodedPassword) {
        // A very long operation.
        throw new UnsupportedOperationException("To be domain");
    }

    public boolean isAuthenticated() {
        return true;
    }
}
