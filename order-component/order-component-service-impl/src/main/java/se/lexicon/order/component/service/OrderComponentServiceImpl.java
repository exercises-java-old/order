package se.lexicon.order.component.service;

import com.so4it.gs.rpc.Routing;
import com.so4it.queue.ParallelQueue;
import se.lexicon.order.component.domain.*;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.gs.rpc.ServiceExport;
import se.lexicon.order.component.mapper.OrderDealMapper;
import se.lexicon.order.component.mapper.OrderMapper;
import se.lexicon.order.componment.dao.OrderDao;
import se.lexicon.order.componment.dao.OrderDealDao;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ServiceExport({OrderComponentService.class})
public class OrderComponentServiceImpl implements OrderComponentService {

    private OrderDao orderDao;
    private OrderDealDao orderDealDao;
    private ParallelQueue<OrderEntity> orderParallelQueue;
    private ParallelQueue<OrderDealEntity> orderDealParallelQueue;


    public OrderComponentServiceImpl(OrderDao orderDao,
                                     OrderDealDao orderDealDao,
                                     ParallelQueue<OrderEntity> orderParallelQueue,
                                     ParallelQueue<OrderDealEntity> orderDealParallelQueue) {

        this.orderDao     = Required.notNull(orderDao,"orderDao");
        this.orderDealDao = Required.notNull(orderDealDao,"orderDealDao");
        this.orderParallelQueue = Required.notNull(orderParallelQueue,"orderParallelQueue");
        this.orderDealParallelQueue = Required.notNull(orderDealParallelQueue,"orderDealParallelQueue");
    }

    public void makeOrderDeal (OrderDeal orderDeal) {
        orderDealParallelQueue.offer(OrderDealMapper.map(orderDeal));
    }

    @Override
    public Orders getOrders(String ssn) {

        return Orders.valueOf(orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).build()).stream().
                map(entity -> Order.builder()
                        .withId(entity.getId())
                        .withSsn(ssn)
                        .withAmount(entity.getAmount())
                        .withInstrument(entity.getInstrument())
                        .withNoOfItems(entity.getNoOfItems())
                        .withMinMaxValue(entity.getMinMaxValue())
                        .withSide(entity.getSide())
                        .withOrderPriceType(entity.getOrderPriceType())
                        .withInsertionTimestamp(entity.getInsertionTimestamp())
                        .withOrderBookId(entity.getOrderBookId())
                        .withOrderDealId(getOrderDeals(entity))
                        .build()).collect(Collectors.toSet()));
    }

    private OrderDeals getOrderDeals (OrderEntity orderEntity) {

        OrderDeals orderDeals = OrderDeals.valueOf(orderDealDao.readAll(
                OrderDealEntity.templateBuilder().withOrderId(orderEntity.getId()).build()).stream()
                .map(odentity -> OrderDeal.builder()
                        .withId(odentity.getId())
                        .withInstrument(odentity.getInstrument())
                        .withNoOfItems(odentity.getNoOfItems())
                        .withPrice(odentity.getPrice())
                        .build())
                .collect(Collectors.toSet()));

        return orderDeals;

    }

    @Override
    public Boolean placeOrder(Order order) {
        return orderParallelQueue.offer(OrderMapper.map(order));
    }

    @Override
    public BigDecimal getTotalOrderValueOfAllOrders() { return orderDao.sum(); }

}
