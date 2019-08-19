package se.lexicon.order.component.test.common.entity;

import se.lexicon.order.component.domain.Money;
import se.lexicon.order.component.domain.OrderPriceType;
import se.lexicon.order.component.domain.Side;
import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class OrderEntityTestBuilder extends AbstractTestBuilder<OrderEntity> {

    private OrderEntity.Builder builder;


    public OrderEntityTestBuilder(OrderEntity.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withSsn("1111111111")
                .withInsertionTimestamp(Instant.now())
                .withAmount(BigDecimal.TEN)
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withMinMaxValue(Money.builder()
                        .withAmount((BigDecimal.valueOf(50d)))
                        .withCurrency(Currency.getInstance("SEK"))
                        .build())
                .withSide(Side.BUY)
                .withOrderPriceType(OrderPriceType.MARKET)
                .withOrderBookId("ABB/SEK");
    }

    public OrderEntityTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public OrderEntityTestBuilder withAmount(BigDecimal amount){
        builder.withAmount(amount);
        return this;
    }

    public OrderEntityTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public OrderEntityTestBuilder withNoOfItems(Integer noOfItems){
        builder.withNoOfItems(noOfItems);
        return this;
    }

     public OrderEntityTestBuilder withMinMaxValue(Money money){
        builder.withMinMaxValue(money);
        return this;
    }

    public OrderEntityTestBuilder withSide(Side side){
        builder.withSide(side);
        return this;
    }
    public OrderEntityTestBuilder withOrderPriceType(OrderPriceType orderPriceType){
        builder.withOrderPriceType(orderPriceType);
        return this;
    }

    public OrderEntityTestBuilder withOrderBookId(String orderBookId){
        builder.withOrderBookId(orderBookId);
        return this;
    }


    public static OrderEntityTestBuilder builder() {
        return new OrderEntityTestBuilder(OrderEntity.builder());
    }

    @Override
    public OrderEntity build() {
        return builder.build();
    }
}
