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
        using = OrderBooks.Deserializer.class
)
@JsonSerialize(
        using = OrderBooks.Serializer.class
)
public class OrderBooks extends AbstractIterable<OrderBook> {

    private static final long serialVersionUID = 1L;

    public OrderBooks() {
        super(OrderBook.class, OrderBooks.class);
    }

    public static OrderBooks valueOf(OrderBook... endpointRegistrations) {
        return (OrderBooks) AbstractIterable.valueOf(endpointRegistrations, OrderBook.class, OrderBooks.class);
    }

    public static OrderBooks valueOf(Set<OrderBook> endpointRegistrations) {
        return (OrderBooks) AbstractIterable.valueOf(endpointRegistrations, OrderBook.class, OrderBooks.class);
    }

    public static OrderBooks valueOf(Iterable<OrderBook> endpointRegistrations) {
        return (OrderBooks) AbstractIterable.valueOf(endpointRegistrations, OrderBook.class, OrderBooks.class);
    }

    public Map<String, OrderBooks> map(Function<OrderBook, String> mapper) {
        return null;
    }

    public static class Serializer extends AbstractIterableSerializer<OrderBook> {
        protected Serializer() {
            super(OrderBook.class, OrderBooks.class);
        }
    }

    public static class Deserializer extends AbstractIterableDeserializer<OrderBook> {
        protected Deserializer() {
            super(OrderBook.class, OrderBooks.class);
        }
    }
}
