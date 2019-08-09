package se.lexicon.order.component.service;


import com.so4it.gs.rpc.Broadcast;
import com.so4it.gs.rpc.Routing;
import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.domain.OrderBooks;
import se.lexicon.order.component.domain.Orders;
import se.lexicon.order.component.entity.OrderEntity;
import java.math.BigDecimal;

public interface OrderComponentService {

    String DEFAULT_BEAN_NAME = "orderComponentService";

    Orders getOrders(@Routing String ssn);

    OrderBooks getOrderBooks (@Routing("getInstrument") OrderEntity orderEntity);

    void placeOrder(@Routing("getSsn") Order order);

    void MatchOrder (@Routing("getInstrument") OrderEntity orderEntity);

//    @Broadcast(reducer = BigDecimalRemoteResultReducer.class)
//    BigDecimal getTotalOrderValueOfAllOrders();

}
