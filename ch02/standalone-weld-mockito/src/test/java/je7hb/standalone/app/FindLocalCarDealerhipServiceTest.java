package je7hb.standalone.app;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.apache.deltaspike.testcontrol.api.mock.ApplicationMockManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


/**
 * Created by ppilgrim on 19/02/2016.
 */
@RunWith(CdiTestRunner.class)
public class FindLocalCarDealerhipServiceTest {
    @Inject
    private FindLocalCarDealershipService dealershipService;

    @BeforeClass
    public static void init()
    {
        ApplicationMockManager applicationMockManager = BeanProvider.getContextualReference(ApplicationMockManager.class);
        applicationMockManager.addMock(new MockFindLocalCarDealershipService());
    }

    @Test
    public void manualMock()
    {
        Optional<Dealer> oneDealer = dealershipService.searchDealer("100", "100");
        assertThat( oneDealer, is(notNullValue()));
        assertThat( oneDealer.isPresent(), is(true));
        assertThat( oneDealer.get().getDealerName(), is("Mock"));
    }

    public static class MockFindLocalCarDealershipService extends FindLocalCarDealershipService {
        @Override
        public Optional<Dealer> searchDealer(String latitude, String longtitude) {
            return Optional.of(new Dealer("Mock"));
        }
    }
}
