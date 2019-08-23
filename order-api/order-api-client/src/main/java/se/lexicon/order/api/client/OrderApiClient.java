package se.lexicon.order.api.client;


import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.domain.OrderDeal;

public interface OrderApiClient {
    String DEFAULT_API_BEAN_NAME = "orderApiClient";

    void makeDeal(OrderDeal orderDeal);
    Boolean placeOrder (Order order);

}
