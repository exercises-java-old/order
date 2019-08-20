package se.lexicon.account.api.client;


public interface OrderApiClient {
    String DEFAULT_API_BEAN_NAME = "orderApiClient";

    void makeDeal(OrderDeal orderDeal);
    Boolean placeOrder (Order order);

}
