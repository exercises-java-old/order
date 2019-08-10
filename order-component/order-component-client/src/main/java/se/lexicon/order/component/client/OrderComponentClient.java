package se.lexicon.order.component.client;

import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.domain.Orders;

import java.util.Set;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface OrderComponentClient {

    Set<String> getInstruments(String ssn);
    Orders getOrders(String instrument, String ssn);

    void placeOrder(Order order);

}
