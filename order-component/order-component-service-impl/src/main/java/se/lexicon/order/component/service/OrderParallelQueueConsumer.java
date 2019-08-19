package se.lexicon.order.component.service;

import com.so4it.queue.ParallelQueueConsumer;
import se.lexicon.market.component.client.MarketOrderComponentClient;
import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.component.event.PlaceOrderEvent;
import se.lexicon.order.componment.dao.OrderDao;

public class OrderParallelQueueConsumer {


    private OrderDao orderDao;
    //private MarketOrderComponentClient marketOrderComponentClient;

    //private MarketApiClient marketClient;


    public OrderParallelQueueConsumer(OrderDao orderDao) {
        this.orderDao = orderDao;
        //this.marketOrderComponentClient = marketOrderComponentClient;
    }

    /**
     * This will be called by the parallel queue framework guaranteeing that only one order for the same
     * Order id will be handled at the time
     *
     * @param placeOrderEvent
     */
    @ParallelQueueConsumer
    public void placeOrder(OrderEntity placeOrderEvent) {

        OrderEntity orderEntity = orderDao.insert(placeOrderEvent);

        // Check account

//        marketOrderComponentClient.placeMarketOrder
//                (MarketOrder.builder()
//                        .withOrderId(orderEntity.getId())
//                        .withInstrument(orderEntity.getInstrument()).build());

    }
}
