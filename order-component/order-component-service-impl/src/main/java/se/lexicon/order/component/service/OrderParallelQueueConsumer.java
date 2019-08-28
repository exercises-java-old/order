package se.lexicon.order.component.service;

import com.so4it.queue.ParallelQueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lexicon.market.api.client.MarketApiClient;
import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.component.event.PlaceOrderEvent;
import se.lexicon.order.component.mapper.EnumMapper;
import se.lexicon.order.component.mapper.MoneyMapper;
import se.lexicon.order.component.mapper.OrderMapper;
import se.lexicon.order.componment.dao.OrderDao;

public class OrderParallelQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderParallelQueueConsumer.class);

    private OrderDao orderDao;
    //private MarketOrderComponentClient marketOrderComponentClient;

    private MarketApiClient marketApiClient;


    public OrderParallelQueueConsumer(OrderDao orderDao, MarketApiClient marketApiClient) {
        this.orderDao = orderDao;
        this.marketApiClient = marketApiClient;
    }

    /**
     * This will be called by the parallel queue framework guaranteeing that only one order for the same
     * Order id will be handled at the time
     *
     * @param placeOrderEvent
     */
    @ParallelQueueConsumer
    public void placeOrderEvent(PlaceOrderEvent placeOrderEvent) {

        LOGGER.info("placeOrderEvent:" + placeOrderEvent);

        OrderEntity orderEntity = orderDao.insert(OrderMapper.map(placeOrderEvent.getOrder()));

        LOGGER.info("placeOrderEvent<orderEntity>:" + orderEntity);

        // Check account

        // If account balance ok, place the order on the market
        boolean ok = marketApiClient.placeMarketOrder(MarketOrder.builder()
                //.withId()
                .withSsn(orderEntity.getSsn())
                .withOrderId(orderEntity.getId())
                .withInstrument(orderEntity.getInstrument())
                .withAmount(orderEntity.getAmount())
                .withInsertionTimestamp(orderEntity.getInsertionTimestamp())
                .withMinMaxValue(MoneyMapper.mapIt(orderEntity.getMinMaxValue()))
                .withNoOfItems(orderEntity.getNoOfItems())
                .withOrderPriceType(EnumMapper.map(orderEntity.getOrderPriceType()))
                .withSide(EnumMapper.map(orderEntity.getSide()))
                .withOrderBookId(orderEntity.getOrderBookId())
                .build());

        // If not OK, reenter placeOrderEvent in the queue
    }
}
