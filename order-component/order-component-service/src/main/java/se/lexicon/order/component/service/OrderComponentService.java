package se.lexicon.order.component.service;


import com.so4it.gs.rpc.Broadcast;
import com.so4it.gs.rpc.Routing;
import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.domain.OrderBooks;
import se.lexicon.order.component.domain.Orders;
import se.lexicon.order.component.entity.OrderEntity;
import java.math.BigDecimal;
import java.util.Set;

public interface OrderComponentService {

    String DEFAULT_BEAN_NAME = "orderComponentService";

    @Broadcast(reducer = InstrumentRemoteResultReducer.class)
    Set<String> getInstruments(String ssn);

    Orders getOrders(@Routing String Instrument, String ssn);

    OrderBooks getOrderBooks (@Routing("getInstrument") OrderEntity orderEntity);

    void placeOrder(@Routing("getInstrument") Order order);

    void MatchOrder (@Routing("getInstrument") OrderEntity orderEntity);

    @Broadcast(reducer = BigDecimalRemoteResultReducer.class)
    BigDecimal getTotalOrderValueOfAllOrders();

}
