package se.lexicon.order.component.test.integration.dao;

import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.component.test.common.entity.OrderEntityTestBuilder;
import com.so4it.test.category.IntegrationTest;
import com.so4it.test.gs.rule.ClearGigaSpaceTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;
import org.openspaces.core.GigaSpace;
import se.lexicon.order.component.test.integration.service.OrderComponentServiceIntegrationTestSuite;
import se.lexicon.order.componment.dao.OrderDao;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@Category(IntegrationTest.class)
public class OrderComponentDaoIntegrationTest {

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = OrderComponentDaoIntegrationTestSuite.SUITE_RULE_CHAIN;

    @Rule
    public ClearGigaSpaceTestRule clearGigaSpaceTestRule = new ClearGigaSpaceTestRule(OrderComponentServiceIntegrationTestSuite.getExportContext().getBean(GigaSpace.class));

    @Test
    public void testInsertingOrder(){
        OrderDao orderDao = OrderComponentDaoIntegrationTestSuite.getExportContext().getBean(OrderDao.class);
        OrderEntity orderEntity = orderDao.insert(OrderEntityTestBuilder.builder().build());
    }

}
