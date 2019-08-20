package se.lexicon.order.api.server;

import se.lexicon.Order.api.*;
import se.lexicon.order.component.client.OrderComponentClient;
import com.so4it.api.Order;
import com.so4it.api.ApiServiceProvider;
import com.so4it.api.util.StreamObserverErrorHandler;
import com.so4it.common.util.object.Required;
import io.grpc.stub.StreamObserver;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */

@ApiServiceProvider(
        value = Account.NAME,
        specification = Account.PATH,
        version = Account.VERSION,
        properties = Account.PROPERTIES)
public class OrderApiServerImpl extends OrderApiServiceGrpc.OrderApiServiceImplBase{

    private OrderComponentClient orderComponentClient;

    public OrderApiServerImpl(OrderComponentClient orderComponentClient) {
        this.orderComponentClient = Required.notNull(orderComponentClient,"orderComponentClient");
    }


}
