package se.lexicon.order.component.mapper;

import com.so4it.common.util.Mapper;
import se.lexicon.order.component.domain.Order;
import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.component.event.PlaceOrderEvent;


public final class OrderMapper {


    public static OrderEntity map(Order order){
        return Mapper.of(order, OrderEntity::builder)
                .map(Order::getId,OrderEntity.Builder::withId)
                .map(Order::getSsn,OrderEntity.Builder::withSsn)
                .map(Order::getAmount,OrderEntity.Builder::withAmount)
                .map(Order::getInstrument,OrderEntity.Builder::withInstrument)
                .map(Order::getNoOfItems,OrderEntity.Builder::withNoOfItems)
                .map(Order::getSide, OrderEntity.Builder::withSide)
                .map(Order::getMinMaxValue, OrderEntity.Builder::withMinMaxValue)
                .map(Order::getOrderPriceType, OrderEntity.Builder::withOrderPriceType)
                .map(Order::getOrderBookId, OrderEntity.Builder::withOrderBookId)
                .map(Order::getInsertionTimestamp, OrderEntity.Builder::withInsertionTimestamp)
                .build(OrderEntity.Builder::build);
    }

    public static PlaceOrderEvent mapEvent(Order order){
        return Mapper.of(order, PlaceOrderEvent::builder)
                .map(order::getId,PlaceOrderEvent.Builder::withId)
                .map(order::getSsn,PlaceOrderEvent.Builder::withSsn)
                .map(()-> order, PlaceOrderEvent.Builder::withOrder)
                .map(()-> 1, PlaceOrderEvent.Builder::withCounter)
                .build(PlaceOrderEvent.Builder::build);
    }

    public static Order map(OrderEntity orderEntity){
        return orderEntity != null ? Mapper.of(orderEntity, Order::builder)
                .map(OrderEntity::getId,Order.Builder::withId)
                .map(OrderEntity::getSsn,Order.Builder::withSsn)
                .map(OrderEntity::getAmount,Order.Builder::withAmount)
                .map(OrderEntity::getInstrument,Order.Builder::withInstrument)
                .map(OrderEntity::getNoOfItems,Order.Builder::withNoOfItems)
                .map(OrderEntity::getSide, Order.Builder::withSide)
                .map(OrderEntity::getMinMaxValue, Order.Builder::withMinMaxValue)
                .map(OrderEntity::getOrderPriceType, Order.Builder::withOrderPriceType)
                .map(OrderEntity::getOrderBookId, Order.Builder::withOrderBookId)
                .map(OrderEntity::getInsertionTimestamp, Order.Builder::withInsertionTimestamp)
                .build(Order.Builder::build) : null;
    }

}
