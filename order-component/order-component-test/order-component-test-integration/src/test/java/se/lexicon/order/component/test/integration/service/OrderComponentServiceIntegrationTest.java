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
        //Set <Currency> currencies = Currency.getAvailableCurrencies();
        //System.out.println(currencies);

        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        Order order = OrderTestBuilder.builder().withSsn("111111").withAmount(BigDecimal.ONE).build();
        orderComponentService.placeOrder(order);
        Orders orders = orderComponentService.getOrders("111111");

        Assert.assertEquals(1, orders.size());
        Assert.assertEquals(order, orders.getFirst());

    }

    @Test
    public void testPlaceTwoOrders() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        Order order1 = OrderTestBuilder.builder().withSsn("111222").withAmount(BigDecimal.ONE).build();
        Order order2 = OrderTestBuilder.builder().withSsn("111222").withAmount(BigDecimal.TEN).build();

        orderComponentService.placeOrder(order1);
        orderComponentService.placeOrder(order2);

        Orders orders = orderComponentService.getOrders("111222");

        Assert.assertEquals(3, orders.size());
    }

    @Test
    public void testMatchOrder() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        Order order1 = OrderTestBuilder.builder()
                //.withId("111111")
                .withSsn("111111")
                .withOrderBookId(OrderBooks.valueOf(
                        new OrderBook.Builder()
                                //.withId("111111")
                                .withInstrument("ABB")
                                .withNoOfItems(100)
                                .withSellOrder(false)
                                .withPhase(Phase.UNKNOWN)
                                .withMinMaxValue(Money.builder()
                                        .withAmount((BigDecimal.valueOf(550d)))
                                        .withCurrency(Currency.getInstance("SEK"))
                                        .build())
                                .build()))
                .build();

        Order order2 = OrderTestBuilder.builder()
                //.withId("222222")
                .withSsn("222222")
                .withOrderBookId(OrderBooks.valueOf(
                                new OrderBook.Builder()
                                        //.withId("222222")
                                        .withInstrument("ABB")
                                        .withNoOfItems(100)
                                        .withSellOrder(true)
                                        .withPhase(Phase.UNKNOWN)
                                        .withMinMaxValue(Money.builder()
                                                .withAmount((BigDecimal.valueOf(500d)))
                                                .withCurrency(Currency.getInstance("SEK"))
                                                .build())
                                        .build()))
                        .build();

        Order order3 = OrderTestBuilder.builder()
                //.withId("333333")
                .withSsn("333333")
                .withOrderBookId(OrderBooks.valueOf(
                        new OrderBook.Builder()
                                //.withId("333333")
                                .withInstrument("ABB")
                                .withNoOfItems(50)
                                .withSellOrder(true)
                                .withPhase(Phase.UNKNOWN)
                                .withMinMaxValue(Money.builder()
                                        .withAmount((BigDecimal.valueOf(480d)))
                                        .withCurrency(Currency.getInstance("SEK"))
                                        .build())
                                .build()))
                .build();

        Order order4 = OrderTestBuilder.builder()
                //.withId("444444")
                .withSsn("444444")
                .withOrderBookId(OrderBooks.valueOf(
                        new OrderBook.Builder()
                                //.withId("444444")
                                .withInstrument("ABB")
                                .withNoOfItems(50)
                                .withSellOrder(true)
                                .withPhase(Phase.UNKNOWN)
                                .withMinMaxValue(Money.builder()
                                        .withAmount((BigDecimal.valueOf(490d)))
                                        .withCurrency(Currency.getInstance("SEK"))
                                        .build())
                                .build()))
                .build();

        Order order5 = OrderTestBuilder.builder()
                //.withId("555555")
                .withSsn("555555")
                .withOrderBookId(OrderBooks.valueOf(
                        new OrderBook.Builder()
                                //.withId("555555")
                                .withInstrument("ABB")
                                .withNoOfItems(100)
                                .withSellOrder(false)
                                .withPhase(Phase.UNKNOWN)
                                .withMinMaxValue(Money.builder()
                                        .withAmount((BigDecimal.valueOf(500d)))
                                        .withCurrency(Currency.getInstance("SEK"))
                                        .build())
                                .build()))
                .build();

        orderComponentService.placeOrder(order1);
        orderComponentService.placeOrder(order2);
        orderComponentService.placeOrder(order3);
        orderComponentService.placeOrder(order4);
        orderComponentService.placeOrder(order5);

        Orders orders1 = orderComponentService.getOrders("111111");
        Orders orders2 = orderComponentService.getOrders("222222");
        Orders orders3 = orderComponentService.getOrders("333333");
        Orders orders4 = orderComponentService.getOrders("444444");
        Orders orders5 = orderComponentService.getOrders("555555");

        Assert.assertEquals(1, orders1.getFirst().getOrderBooks().size());
        Assert.assertEquals(1, orders2.getFirst().getOrderBooks().size());
        Assert.assertEquals(1, orders3.getFirst().getOrderBooks().size());
        Assert.assertEquals(1, orders4.getFirst().getOrderBooks().size());
        Assert.assertEquals(2, orders5.getFirst().getOrderBooks().size());

        Assert.assertNotEquals(order1, orders1.getFirst());
    }

    @Test
    public void testGetAllOrderComponent() {
        OrderComponentService orderComponentService = OrderComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("111111").withAmount(BigDecimal.TEN).build());

        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withAmount(BigDecimal.ONE).build());
        orderComponentService.placeOrder(OrderTestBuilder.builder().withSsn("222222").withAmount(BigDecimal.TEN).build());

        Orders orders = orderComponentService.getOrders("111111");
        Assert.assertEquals(2, orders.size());
        //Assert.assertEquals(11, orders.asStream().forEach(item -> item.getAmount().c));

        //Assert.assertEquals(BigDecimal.valueOf(22.0), orderComponentService.getTotalOrderValueOfAllOrders());

    }

}
