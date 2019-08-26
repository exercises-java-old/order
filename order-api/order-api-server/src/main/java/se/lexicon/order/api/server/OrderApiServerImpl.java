package se.lexicon.order.api.server;

import se.lexicon.order.*;
import se.lexicon.order.Money;
import se.lexicon.order.OrderPriceType;
import se.lexicon.order.Side;
import se.lexicon.order.component.client.OrderComponentClient;
import com.so4it.api.Order;
import com.so4it.api.ApiServiceProvider;
import com.so4it.api.util.StreamObserverErrorHandler;
import com.so4it.common.util.object.Required;
import io.grpc.stub.StreamObserver;
import se.lexicon.order.component.domain.*;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */

@ApiServiceProvider(
        value = Order.NAME,
        specification = Order.PATH,
        version = Order.VERSION,
        properties = Order.PROPERTIES)
public class OrderApiServerImpl extends OrderApiServiceGrpc.OrderApiServiceImplBase{

    private OrderComponentClient orderComponentClient;

    public OrderApiServerImpl(OrderComponentClient orderComponentClient) {
        this.orderComponentClient = Required.notNull(orderComponentClient,"orderComponentClient");
    }

    @Override
    public void placeOrder(PlaceOrderRequest request, StreamObserver<PlaceOrderResponse> responseObserver) {
        StreamObserverErrorHandler.of(responseObserver).onError(() -> {

           orderComponentClient.placeOrder
                    (se.lexicon.order.component.domain.Order.builder()
                            .withSsn(request.getSsn())
                            .withAmount(BigDecimal.valueOf(request.getAmount()))
                            .withInsertionTimestamp(Instant.now())
                            .withInstrument(request.getInstrument())
                            .withNoOfItems(request.getNoOfItems())
                            .withOrderBookId(request.getOrderBookId())
                            .withSide(mapSide(request.getSide()))
                            .withOrderPriceType(mapOrderPrice(request.getOrderPriceType()))
                            .withMinMaxValue(mapMoney (request.getMinMaxValue()))
                            .build());

            responseObserver.onNext(PlaceOrderResponse.newBuilder().setOk(true).build());
            responseObserver.onCompleted();
        }, "Failed creating order request order");
    }

    @Override
    public void makeOrderDeal(OrderDealRequest request, StreamObserver<OrderDealResponse> responseObserver) {
        StreamObserverErrorHandler.of(responseObserver).onError(() -> {

            orderComponentClient.makeOrderDeal
                    (OrderDeal.builder()
                            .withSsn(request.getSsn())
                            .withOrderId(request.getSsn())
                            .withInstrument(request.getOrderid())
                            .withNoOfItems(request.getNoOfItems())
                            .withPrice(mapMoney (request.getPrice()))
                            .build());

            responseObserver.onNext(OrderDealResponse.newBuilder().build());
            responseObserver.onCompleted();
        }, "Failed creating order request order");
    }


    private se.lexicon.order.component.domain.Side mapSide (se.lexicon.order.Side value) {
        if (value == se.lexicon.order.Side.BUY) return se.lexicon.order.component.domain.Side.BUY;
        return se.lexicon.order.component.domain.Side.SELL;
    }

    private se.lexicon.order.component.domain.OrderPriceType mapOrderPrice (se.lexicon.order.OrderPriceType value) {

        if (value == se.lexicon.order.OrderPriceType.MARKET) return se.lexicon.order.component.domain.OrderPriceType.MARKET;
        if (value == se.lexicon.order.OrderPriceType.LIMIT) return se.lexicon.order.component.domain.OrderPriceType.LIMIT;
        return se.lexicon.order.component.domain.OrderPriceType.FULL_LIMIT;
    }

    private se.lexicon.order.component.domain.Money mapMoney (se.lexicon.order.Money money){
        return se.lexicon.order.component.domain.Money.builder()
                .withAmount(BigDecimal.valueOf(money.getAmount()))
                .withCurrency(Currency.getInstance(money.getCurrency()))
                .build();
    }

}
