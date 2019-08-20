package se.lexicon.order.component.service;

import com.so4it.queue.ParallelQueueConsumer;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.component.event.MakeDealEvent;
import se.lexicon.order.componment.dao.OrderDao;
import se.lexicon.order.componment.dao.OrderDealDao;

public class OrderDealParallelQueueConsumer {


    private OrderDealDao orderDealDao;

    public OrderDealParallelQueueConsumer(OrderDealDao orderDealDao) {
        this.orderDealDao = orderDealDao;
    }

    /**
     * This will be called by the parallel queue framework guaranteeing that only one order for the same
     * orderDeal id will be handled at the time
     *
     * @param makeDealEvent
     */
    @ParallelQueueConsumer
    public void makeOrderDeal(OrderDealEntity makeDealEvent) {

        OrderDealEntity orderDealEntity = orderDealDao.insert(makeDealEvent);

        // Update the ACCOUNT

        // Update VPC

        //System.out.println(orderDealEntity);

    }
}
