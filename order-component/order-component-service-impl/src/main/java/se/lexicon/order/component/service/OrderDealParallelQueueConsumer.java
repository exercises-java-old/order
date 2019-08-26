package se.lexicon.order.component.service;

import com.so4it.queue.ParallelQueueConsumer;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.event.MakeDealEvent;
import se.lexicon.order.component.mapper.OrderDealMapper;
import se.lexicon.order.componment.dao.OrderDao;
import se.lexicon.order.componment.dao.OrderDealDao;

public class OrderDealParallelQueueConsumer {


    private OrderDealDao orderDealDao;
    private OrderDao orderDao;

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
    public void makeOrderDeal(MakeDealEvent makeDealEvent) {

        if (!orderDao.exists(makeDealEvent.getOrderDeal().getOrderId())) {
            System.out.println("UNKNOWN ORDER_ID TO DEAL " + makeDealEvent.getOrderDeal());
            return;
        }


        OrderDealEntity orderDealEntity = orderDealDao.insert(OrderDealMapper.map(makeDealEvent.getOrderDeal()));

        // Update the ACCOUNT

        // Update VPC

        System.out.println(makeDealEvent);

    }
}
