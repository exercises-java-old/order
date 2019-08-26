package se.lexicon.order.component.client;

import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.domain.OrderDeal;
import se.lexicon.order.component.domain.Orders;

import java.util.Set;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface OrderComponentClient {

    void makeOrderDeal (OrderDeal orderDeal);

    Orders getOrders(String ssn);

    Boolean placeOrder(Order order);

}
