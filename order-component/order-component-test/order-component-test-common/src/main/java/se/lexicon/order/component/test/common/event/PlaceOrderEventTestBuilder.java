package se.lexicon.order.component.test.common.event;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.event.PlaceOrderEvent;
import se.lexicon.order.component.test.common.domain.OrderTestBuilder;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class PlaceOrderEventTestBuilder extends AbstractTestBuilder<PlaceOrderEvent> {

    private PlaceOrderEvent.Builder builder;


    public PlaceOrderEventTestBuilder(PlaceOrderEvent.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withSsn(OrderTestBuilder.builder().build().getSsn())
                .withOrder(OrderTestBuilder.builder().build())
                .withCounter(1);
    }

    public PlaceOrderEventTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public static PlaceOrderEventTestBuilder builder() {
        return new PlaceOrderEventTestBuilder(PlaceOrderEvent.builder());
    }

    @Override
    public PlaceOrderEvent build() {
        return builder.build();
    }
}
