package se.lexicon.order.component.test.common.domain;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.domain.Money;
import se.lexicon.order.component.domain.OrderDeal;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class OrderDealTestBuilder extends AbstractTestBuilder<OrderDeal> {

    private OrderDeal.Builder builder;

    public OrderDealTestBuilder(OrderDeal.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withSsn("1111111111")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withPrice(Money.builder()
                    .withAmount((BigDecimal.valueOf(50d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .withOrderId("222222222")

//                .withOrderBookId (OrderBooks.valueOf(
//                        new OrderBook.Builder()
//                            //.withId("1111111111")
//                            .withInstrument("ABB")
//                            .withNoOfItems(100)
//                            .withSide(Side.BUY)
//                            .withPhase(Phase.UNKNOWN)
//                            .withMinMaxValue(Money.builder()
//                                .withAmount((BigDecimal.valueOf(500d)))
//                                .withCurrency(Currency.getInstance("SEK"))
//                                .build())
                            .build();
    }

    public static OrderDealTestBuilder builder() {
        return new OrderDealTestBuilder(OrderDeal.builder());
    }


    public OrderDealTestBuilder withId(String id){
        builder.withId(id);
        return this;
    }

    public OrderDealTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public OrderDealTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public OrderDealTestBuilder withNoOfItems(Integer noOfItems){
        builder.withNoOfItems(noOfItems);
        return this;
    }

    public OrderDealTestBuilder withPrice(Money money){
        builder.withPrice(money);
        return this;
    }


    public OrderDealTestBuilder withOrderId(String orderId){
        builder.withOrderId(orderId);
        return this;
    }


    @Override
    public OrderDeal build() {
        return builder.build();
    }
}
