package se.lexicon.order.component.client;

import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.domain.OrderDeal;
import se.lexicon.order.component.domain.Orders;
import se.lexicon.order.component.service.OrderComponentService;
import com.so4it.common.util.object.Required;

import java.util.Set;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class OrderComponentClientImpl implements OrderComponentClient{

    private OrderComponentService orderComponentService;

    public OrderComponentClientImpl(OrderComponentService orderComponentService) {
        this.orderComponentService = Required.notNull(orderComponentService,"orderComponentService");
    }

    @Override
    public void makeOrderDeal (OrderDeal orderDeal){ orderComponentService.makeOrderDeal(orderDeal); }

    @Override
    public Orders getOrders(String ssn){ return orderComponentService.getOrders(ssn); }

    @Override
    public Boolean placeOrder(Order order) { return orderComponentService.placeOrder(order); }

}
