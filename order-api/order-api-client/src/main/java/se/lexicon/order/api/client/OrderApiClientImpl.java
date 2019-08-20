package se.lexicon.order.api.client;

import com.so4it.common.util.object.Required;
import com.so4it.ft.core.FaultTolerantBean;
import com.so4it.metric.springframework.MetricsBean;

@FaultTolerantBean(groupKey = OrderApiClientImpl.ORDER_API_CLIENT_NAME)
@MetricsBean(name = OrderApiClientImpl.ORDER_API_CLIENT_NAME)
public class OrderApiClientImpl implements OrderApiClient {

    static final String ORDER_API_CLIENT_NAME = "ORDER_API_CLIENT";

    private final OrderApiServiceGrpc.OrderApiServiceBlockingStub OrderApiServiceBlockingStub;

    OrderApiClientImpl(OrderApiServiceGrpc.OrderApiServiceBlockingStub orderApiServiceBlockingStub) {
        this.orderApiServiceBlockingStub = Required.notNull(orderApiServiceBlockingStub, "orderApiServiceBlockingStub");
    }

    void makeDeal(OrderDeal orderDeal) {}

    Boolean placeOrder (Order order) {}

}
