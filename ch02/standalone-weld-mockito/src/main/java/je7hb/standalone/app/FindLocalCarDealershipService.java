package je7hb.standalone.app;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

/**
 * Created by ppilgrim on 19/02/2016.
 */
@ApplicationScoped
public class FindLocalCarDealershipService {

    public Optional<Dealer> searchDealer(String latitude, String longtitude) {
        return Optional.of( new Dealer( "Milton Keynes Master Cars"));
    }
}
