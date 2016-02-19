package je7hb.standalone.app;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.apache.deltaspike.testcontrol.api.mock.DynamicMockManager;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
/**
 * Created by ppilgrim on 19/02/2016.
 */
@RunWith(CdiTestRunner.class)
public class SecureDomainContextMockitoTest {

    @Inject
    private MicroserviceBoundary microserviceBoundary;

    @Inject
    private SecureDomainContext secureDomainContext;

    @Inject
    private DynamicMockManager mockManager;

    @Test
    public void mockitoMockAsCdiBean()
    {
        assertThat( microserviceBoundary, is(notNullValue()));
        assertThat( secureDomainContext, is(notNullValue()));
        assertThat( mockManager, is(notNullValue()));

        final SecureDomainContext mockedSecureDomainContextBean = mock(SecureDomainContext.class);
        when(mockedSecureDomainContextBean.isAuthenticated()).thenReturn(false);
        mockManager.addMock(secureDomainContext);

        assertThat( mockedSecureDomainContextBean.isAuthenticated(), is(false));
        mockedSecureDomainContextBean.authenticate("Trevor_Nelson","Soul!Dance".getBytes());

        verify(mockedSecureDomainContextBean).isAuthenticated();
    }
}
