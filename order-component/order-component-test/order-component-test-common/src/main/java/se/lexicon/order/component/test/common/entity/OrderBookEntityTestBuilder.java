package se.lexicon.order.component.test.common.entity;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.domain.Money;
import se.lexicon.order.component.domain.Phase;
import se.lexicon.order.component.entity.OrderBookEntity;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class OrderBookEntityTestBuilder extends AbstractTestBuilder<OrderBookEntity> {

    private OrderBookEntity.Builder builder;


    public OrderBookEntityTestBuilder(OrderBookEntity.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withOrderId("1111111111")
                .withSsn("1111111111")
                .withNoOfItems(100)
                .withMinMaxValue(Money.builder().withAmount(BigDecimal.ONE).withCurrency(Currency.getInstance("SEK")).build())
                .withInstrument("ABB")
                .withPhase(Phase.UNKNOWN)
                .withSellOrder(false)
                //.withMatchingOrderId("")
                .build();
    }

    public OrderBookEntityTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public OrderBookEntityTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public OrderBookEntityTestBuilder withNoOfItems(int noOfItems){
        builder.withNoOfItems(noOfItems);
        return this;
    }

    public static OrderBookEntityTestBuilder builder() {
        return new OrderBookEntityTestBuilder(OrderBookEntity.builder());
    }

    @Override
    public OrderBookEntity build() {
        return builder.build();
    }
}
