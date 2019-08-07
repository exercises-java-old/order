package se.lexicon.order.component.test.common.entity;

import se.lexicon.order.component.domain.OrderBooks;
import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;

import java.math.BigDecimal;
import java.time.Instant;

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
                .withAmount(BigDecimal.TEN);
    }

    public OrderEntityTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public OrderEntityTestBuilder withAmount(BigDecimal amount){
        builder.withAmount(amount);
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
