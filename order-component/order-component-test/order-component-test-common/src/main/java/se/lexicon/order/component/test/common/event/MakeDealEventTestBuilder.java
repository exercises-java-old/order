package se.lexicon.order.component.test.common.event;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.event.MakeDealEvent;
import se.lexicon.order.component.test.common.domain.OrderDealTestBuilder;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class MakeDealEventTestBuilder extends AbstractTestBuilder<MakeDealEvent> {

    private MakeDealEvent.Builder builder;


    public MakeDealEventTestBuilder(MakeDealEvent.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withSsn(OrderDealTestBuilder.builder().build().getSsn())
                .withOrderDeal(OrderDealTestBuilder.builder().build())
                .withCounter(1);
    }

    public MakeDealEventTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public static MakeDealEventTestBuilder builder() {
        return new MakeDealEventTestBuilder(MakeDealEvent.builder());
    }

    @Override
    public MakeDealEvent build() {
        return builder.build();
    }
}
