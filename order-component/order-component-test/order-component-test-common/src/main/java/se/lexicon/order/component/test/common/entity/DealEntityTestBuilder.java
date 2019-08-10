package se.lexicon.order.component.test.common.entity;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.entity.DealEntity;

public class DealEntityTestBuilder extends AbstractTestBuilder<DealEntity>{

    private DealEntity.Builder builder;

    public DealEntityTestBuilder(DealEntity.Builder builder){
        this.builder= Required.notNull(builder,"builder");
        this.builder
                .withId("111111111")
                .withInstrument("ABb")
                .withBuyOrderId("22222222")
                .withSellOrderId("22222222")
                .build();
    }

    public DealEntityTestBuilder withId(String id){
        builder.withId(id);
        return this;
    }

    public DealEntityTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public DealEntityTestBuilder withBuyOrderId(String buyOrderId){
        builder.withBuyOrderId(buyOrderId);
        return this;
    }

    public DealEntityTestBuilder withSellOrderId(String sellOrderId){
        builder.withSellOrderId(sellOrderId);
        return this;
    }

    public static DealEntityTestBuilder builder(){
        return new DealEntityTestBuilder(DealEntity.builder());
    }


    @Override
    public DealEntity build() {
        return builder.build();
    }
}
