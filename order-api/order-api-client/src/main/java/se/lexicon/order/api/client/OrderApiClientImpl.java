package se.lexicon.order.api.client;

import com.so4it.common.util.object.Required;
import com.so4it.ft.core.FaultTolerantBean;
import com.so4it.metric.springframework.MetricsBean;
import se.lexicon.order.OrderApiServiceGrpc;
import se.lexicon.order.OrderDealRequest;
import se.lexicon.order.PlaceOrderRequest;
import se.lexicon.order.component.domain.*;

@FaultTolerantBean(groupKey = OrderApiClientImpl.ORDER_API_CLIENT_NAME)
@MetricsBean(name = OrderApiClientImpl.ORDER_API_CLIENT_NAME)
public class OrderApiClientImpl implements OrderApiClient {

    static final String ORDER_API_CLIENT_NAME = "ORDER_API_CLIENT";

    private OrderApiServiceGrpc.OrderApiServiceBlockingStub orderService;

    public OrderApiClientImpl(OrderApiServiceGrpc.OrderApiServiceBlockingStub orderService) {
        this.orderService = orderService;
    }

    @Override
    public Boolean placeOrder(Order order) {
        se.lexicon.order.PlaceOrderResponse response = orderService.placeOrder(PlaceOrderRequest.newBuilder()
                //.setId(orderOrder.getId())
                .setSsn(order.getSsn())
                .setAmount(order.getAmount().floatValue())
                .setInstrument(order.getInstrument())
                .setNoOfItems(order.getNoOfItems())
                .setMinMaxValue(mapMoney(order.getMinMaxValue()))
                .setSide(mapSide(order.getSide()))
                .setOrderPriceType(mapOrderPrice(order.getOrderPriceType()))
                .build());

        return response.getOk();

    }

    @Override
    public void makeDeal(OrderDeal orderDeal) {

        se.lexicon.order.OrderDealResponse response = orderService.makeOrderDeal(OrderDealRequest.newBuilder()
                //.setId(orderOrder.getId())
                .setSsn(orderDeal.getSsn())
                .setInstrument(orderDeal.getInstrument())
                .setNoOfItems(orderDeal.getNoOfItems())
                .setPrice(mapMoney(orderDeal.getPrice()))
                .build());
    }

    private se.lexicon.order.Side mapSide (Side value) {
        if (value == Side.BUY) return se.lexicon.order.Side.BUY;
        return se.lexicon.order.Side.SELL;
    }

    private se.lexicon.order.OrderPriceType mapOrderPrice (OrderPriceType value) {

        if (value == OrderPriceType.MARKET) return se.lexicon.order.OrderPriceType.MARKET;
        if (value == OrderPriceType.LIMIT) return se.lexicon.order.OrderPriceType.LIMIT;
        return se.lexicon.order.OrderPriceType.FULL_LIMIT;
    }

    private se.lexicon.order.Money mapMoney (Money money){
        return se.lexicon.order.Money.newBuilder()
                .setAmount(money.getAmount().floatValue())
                .setCurrency(money.getCurrency().getCurrencyCode())
                .build();
    }

}
