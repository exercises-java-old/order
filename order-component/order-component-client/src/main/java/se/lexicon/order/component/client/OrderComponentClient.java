package se.lexicon.order.component.client;

import se.lexicon.order.component.domain.Order;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface OrderComponentClient {

    void placeOrder(Order order);

}
