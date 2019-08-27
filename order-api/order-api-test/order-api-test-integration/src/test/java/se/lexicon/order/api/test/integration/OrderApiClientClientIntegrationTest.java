package se.lexicon.order.api.test.integration;

import com.so4it.test.common.probe.SatisfiedWhenTrueReturned;
import org.junit.Assert;
import se.lexicon.order.api.client.OrderApiClient;
import se.lexicon.order.api.client.OrderApiProvider;
import com.so4it.api.interceptor.request.RequestContextClientInterceptor;
import com.so4it.api.interceptor.request.RequestContextServerInterceptor;
import com.so4it.api.test.common.ApiFrameworkBootstrapTestRule;
import com.so4it.api.test.common.ApiFrameworkCommonTest;
import com.so4it.api.test.common.SatisfiedWhenClientConnected;
import com.so4it.common.bean.BeanContext;
import com.so4it.test.category.IntegrationTest;
import com.so4it.test.common.probe.Poller;
import com.so4it.test.gs.rule.ClearGigaSpaceTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;
import org.openspaces.core.GigaSpace;
import se.lexicon.order.component.test.common.domain.OrderDealTestBuilder;
import se.lexicon.order.component.test.common.domain.OrderTestBuilder;


/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@Category(IntegrationTest.class)
public class OrderApiClientClientIntegrationTest {

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = OrderApiIntegrationTestSuite.SUITE_RULE_CHAIN;


    @Rule
    public ClearGigaSpaceTestRule clearGigaSpaceTestRule = new ClearGigaSpaceTestRule(OrderApiIntegrationTestSuite.getComponentRule().getBean(GigaSpace.class));

    private static final BeanContext BEAN_CONTEXT = ApiFrameworkCommonTest.createClientBeanContext(OrderApiIntegrationTestSuite.DYNAMIC_CONFIGURATION);

    @Rule
    public ApiFrameworkBootstrapTestRule apiFrameworkBootstrapTestRule = ApiFrameworkBootstrapTestRule.builder()
            .withBeanContext(BEAN_CONTEXT)
            .withDynamicConfiguration(OrderApiIntegrationTestSuite.DYNAMIC_CONFIGURATION)
            .withApiRegistryClient(OrderApiIntegrationTestSuite.API_REGISTRY)
            .withImports(OrderApiProvider.class)
            .withExports()
            .withClientInterceptors(new RequestContextClientInterceptor())
            .withServerInterceptors(new RequestContextServerInterceptor())
            .build();



    @Test
    public void testPlaceOrder() throws Exception {
        OrderApiClient orderApiClient = BEAN_CONTEXT.getBean(OrderApiClient.class);
        Poller.pollAndCheck(SatisfiedWhenClientConnected.create(orderApiClient));

        Boolean ok = orderApiClient.placeOrder(OrderTestBuilder.builder().build());

        Assert.assertTrue(ok);

        Thread.sleep(3000);
    }

    @Test
    public void testMakeDeal() throws Exception {
        OrderApiClient orderApiClient = BEAN_CONTEXT.getBean(OrderApiClient.class);
        Poller.pollAndCheck(SatisfiedWhenClientConnected.create(orderApiClient));

        orderApiClient.makeDeal(OrderDealTestBuilder.builder().build());

        Thread.sleep(3000);
    }

}