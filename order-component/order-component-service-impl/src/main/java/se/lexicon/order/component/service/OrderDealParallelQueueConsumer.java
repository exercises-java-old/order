package se.lexicon.order.component.service;

import com.so4it.queue.ParallelQueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.component.event.MakeDealEvent;
import se.lexicon.order.component.mapper.OrderDealMapper;
import se.lexicon.order.componment.dao.OrderDao;
import se.lexicon.order.componment.dao.OrderDealDao;

public class OrderDealParallelQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDealParallelQueueConsumer.class);

    private OrderDealDao orderDealDao;
    private OrderDao     orderDao;

    public OrderDealParallelQueueConsumer(OrderDealDao orderDealDao, OrderDao orderDao) {
        this.orderDealDao = orderDealDao;
        this.orderDao = orderDao;
    }

    /**
     * This will be called by the parallel queue framework guaranteeing that only one order for the same
     * orderDeal id will be handled at the time
     *
     * @param makeDealEvent
     */
    @ParallelQueueConsumer
    public void makeOrderDealEvent(MakeDealEvent makeDealEvent) {

        LOGGER.info("makeOrderDealEvent: " + makeDealEvent);

        OrderEntity orderEntity = orderDao.readByIdIfExists(makeDealEvent.getOrderDeal().getOrderId());

        LOGGER.info("makeOrderDealEvent<Order>: " + orderEntity);

        if (orderEntity == null || !makeDealEvent.getOrderDeal().getSsn().equals(orderEntity.getSsn())) {
            System.out.println("UNKNOWN ORDER_ID TO DEAL " + makeDealEvent.getOrderDeal());
            return;
        }

        OrderDealEntity orderDealEntity = orderDealDao.insert(OrderDealMapper.map(makeDealEvent.getOrderDeal()));

        // Update the ACCOUNT

        // Update VPC

    }
}
