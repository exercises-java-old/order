package se.lexicon.order.component.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.so4it.common.domain.AbstractIterable;
import com.so4it.common.domain.AbstractIterableDeserializer;
import com.so4it.common.domain.AbstractIterableSerializer;
import com.so4it.common.domain.DomainClass;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@DomainClass
@JsonDeserialize(
        using = Orders.Deserializer.class
)
@JsonSerialize(
        using = Orders.Serializer.class
)
public class Orders extends AbstractIterable<Order> {

    private static final long serialVersionUID = 1L;

    public Orders() {
        super(Order.class, Orders.class);
    }

    public static Orders valueOf(Order... endpointRegistrations) {
        return (Orders) AbstractIterable.valueOf(endpointRegistrations, Order.class, Orders.class);
    }

    public static Orders valueOf(Set<Order> endpointRegistrations) {
        return (Orders) AbstractIterable.valueOf(endpointRegistrations, Order.class, Orders.class);
    }

    public static Orders valueOf(Iterable<Order> endpointRegistrations) {
        return (Orders) AbstractIterable.valueOf(endpointRegistrations, Order.class, Orders.class);
    }

    public Map<String, Orders> map(Function<Order, String> mapper) {
        return null;
    }

    public static class Serializer extends AbstractIterableSerializer<Order> {
        protected Serializer() {
            super(Order.class, Orders.class);
        }
    }

    public static class Deserializer extends AbstractIterableDeserializer<Order> {
        protected Deserializer() {
            super(Order.class, Orders.class);
        }
    }
}
