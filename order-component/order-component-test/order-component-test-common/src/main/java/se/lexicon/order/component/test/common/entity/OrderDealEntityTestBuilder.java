package se.lexicon.order.component.test.common.entity;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.domain.Money;
import se.lexicon.order.component.entity.OrderDealEntity;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

public class OrderDealEntityTestBuilder extends AbstractTestBuilder<OrderDealEntity>{

    private OrderDealEntity.Builder builder;

    public OrderDealEntityTestBuilder(OrderDealEntity.Builder builder){

        this.builder= Required.notNull(builder,"builder");
        this.builder
                .withId("111111111")
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withOrderId1("22222222")
                .withOrderId2("33333333")
                .withClosed(false)
                .withPrice(Money.builder().withAmount(BigDecimal.ONE).withCurrency(Currency.getInstance("SEK")).build())
                .build();
    }

    public OrderDealEntityTestBuilder withId(String id){
        builder.withId(id);
        return this;
    }

    public OrderDealEntityTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public OrderDealEntityTestBuilder withNoOfItems(Integer noOfItems){
        builder.withNoOfItems(noOfItems);
        return this;
    }

    public OrderDealEntityTestBuilder withOrderId1(String orderId1){
        builder.withOrderId1(orderId1);
        return this;
    }

    public OrderDealEntityTestBuilder withOrderId2(String orderId2){
        builder.withOrderId2(orderId2);
        return this;
    }

    public static OrderDealEntityTestBuilder builder(){
        return new OrderDealEntityTestBuilder(OrderDealEntity.builder());
    }


    @Override
    public OrderDealEntity build() {
        return builder.build();
    }
}
