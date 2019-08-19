package se.lexicon.order.component.service;


import com.so4it.gs.rpc.Broadcast;
import com.so4it.gs.rpc.Routing;
import se.lexicon.order.component.domain.*;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.entity.OrderEntity;
import java.math.BigDecimal;
import java.util.Set;

public interface OrderComponentService {

    String DEFAULT_BEAN_NAME = "orderComponentService";

    void makeOrderDeal (@Routing("getSsn") OrderDeal orderDeal);

    Boolean placeOrder(@Routing("getSsn") Order order);
    Orders getOrders(@Routing String ssn);

    @Broadcast(reducer = BigDecimalRemoteResultReducer.class)
    BigDecimal getTotalOrderValueOfAllOrders();

}
