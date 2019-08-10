package se.lexicon.order.component.test.integration.service;

import com.so4it.test.category.IntegrationTest;
import com.so4it.test.common.Assert;
import com.so4it.test.gs.rule.ClearGigaSpaceTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;
import org.openspaces.core.GigaSpace;
import se.lexicon.order.component.domain.*;
import se.lexicon.order.component.service.OrderComponentService;
import se.lexicon.order.component.test.common.domain.OrderTestBuilder;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@Category(IntegrationTest.class)
public class OrderComponentServiceIntegrationTest {

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = OrderComponentServiceIntegrationTestSuite.SUITE_RULE_CHAIN;

    @Rule
    public ClearGigaSpaceTestRule clearGigaSpaceTestRule = new ClearGigaSpaceTestRule(OrderComponentServiceIntegrationTestSuite.getExportContext().getBean(GigaSpace.class));

    @Test
    public void testOrderComponentServiceExists() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);
        Assert.assertNotNull(orderComponentService);
    }

    @Test
    public void testPlaceOrder() {
        //Set<Currency> currencies = Currency.getAvailableCurrencies();
        //System.out.println(currencies);

        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        Order order = OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build();
        orderComponentService.placeOrder(order);
        Orders orders = orderComponentService.getOrders("ABB","111111");

        Assert.assertEquals(1, orders.size());
        Assert.assertEquals(order, orders.getFirst());
        Assert.assertEquals(1, orders.getFirst().getOrderBooks().size());

    }

    @Test
    public void testPlaceTwoOrders() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        Order order1 = OrderTestBuilder.builder().withSsn("111222").withInstrument("ABB").withAmount(BigDecimal.ONE).build();
        Order order2 = OrderTestBuilder.builder().withSsn("111222").withInstrument("ABB").withAmount(BigDecimal.TEN).build();

        orderComponentService.placeOrder(order1);
        orderComponentService.placeOrder(order2);

        Orders orders = orderComponentService.getOrders("ABB","111222");

        Assert.assertEquals(2, orders.size());
    }

    @Test
    public void testMatchOrder() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        Order order1 = OrderTestBuilder.builder()
                //.withId("111111")
                .withSsn("111111")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.BUY)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(550d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        Order order2 = OrderTestBuilder.builder()
                //.withId("222222")
                .withSsn("222222")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.SELL)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(500d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        Order order3 = OrderTestBuilder.builder()
                //.withId("333333")
                .withSsn("333333")
                .withInstrument("ABB")
                .withNoOfItems(50)
                .withSide(Side.SELL)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(480d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        Order order4 = OrderTestBuilder.builder()
                //.withId("444444")
                .withSsn("444444")
                .withInstrument("ABB")
                .withNoOfItems(50)
                .withSide(Side.SELL)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(490d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        Order order5 = OrderTestBuilder.builder()
                //.withId("555555")
                .withSsn("555555")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.BUY)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(500d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .build();

        orderComponentService.placeOrder(order1);
        orderComponentService.placeOrder(order2);
        orderComponentService.placeOrder(order3);
        orderComponentService.placeOrder(order4);
        orderComponentService.placeOrder(order5);

        Orders orders1 = orderComponentService.getOrders("ABB","111111");
        Orders orders2 = orderComponentService.getOrders("ABB","222222");
        Orders orders3 = orderComponentService.getOrders("ABB","333333");
        Orders orders4 = orderComponentService.getOrders("ABB","444444");
        Orders orders5 = orderComponentService.getOrders("ABB","555555");

        Assert.assertEquals(1, orders1.getFirst().getOrderBooks().size());
        Assert.assertEquals(1, orders2.getFirst().getOrderBooks().size());
        Assert.assertEquals(1, orders3.getFirst().getOrderBooks().size());
        Assert.assertEquals(1, orders4.getFirst().getOrderBooks().size());
        Assert.assertEquals(2, orders5.getFirst().getOrderBooks().size());

    }

    @Test
    public void testGetAllOrderComponent() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());

        Orders orders1 = orderComponentService.getOrders("ABB","111111");
        Assert.assertEquals(2, orders1.size());

        Orders orders2 = orderComponentService.getOrders("SAAB","111111");
        Assert.assertEquals(1, orders2.size());

    }

    @Test
    public void testGetInstruments() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("ERICSSON").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.TEN).build());

        Assert.assertEquals(3, orderComponentService.getInstruments("222222").size());
        Assert.assertEquals(2, orderComponentService.getInstruments("111111").size());

        Assert.assertEquals("[ABB, SAAB, ERICSSON]", orderComponentService.getInstruments("222222").toString());
        Assert.assertEquals("[ABB, SAAB]", orderComponentService.getInstruments("111111").toString());

    }

    @Test
    public void testGetTotalOrderValue() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("ABB").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("SAAB").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("ERICSSON").withAmount(BigDecimal.TEN).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withInstrument("ABB").withAmount(BigDecimal.TEN).build());

        Assert.assertEquals(BigDecimal.valueOf(52.0), orderComponentService.getTotalOrderValueOfAllOrders());

    }

}
