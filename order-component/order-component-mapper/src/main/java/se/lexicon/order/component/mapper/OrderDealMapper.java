package se.lexicon.order.component.mapper;

import com.so4it.common.util.Mapper;
import se.lexicon.order.component.domain.OrderDeal;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.event.MakeDealEvent;


public final class OrderDealMapper {

    public static OrderDealEntity map(OrderDeal orderDeal){
        return Mapper.of(orderDeal, OrderDealEntity::builder)
                .map(OrderDeal::getId,OrderDealEntity.Builder::withId)
                .map(OrderDeal::getSsn,OrderDealEntity.Builder::withSsn)
                .map(OrderDeal::getOrderId,OrderDealEntity.Builder::withOrderId)
                .map(OrderDeal::getInstrument,OrderDealEntity.Builder::withInstrument)
                .map(OrderDeal::getNoOfItems,OrderDealEntity.Builder::withNoOfItems)
                .map(OrderDeal::getPrice, OrderDealEntity.Builder::withPrice)
                .build(OrderDealEntity.Builder::build);
    }

    public static MakeDealEvent mapEvent(OrderDeal orderDeal){
        return Mapper.of(orderDeal, MakeDealEvent::builder)
                .map(OrderDeal::getId,MakeDealEvent.Builder::withId)
                .map(OrderDeal::getSsn,MakeDealEvent.Builder::withSsn)
                .map(()-> orderDeal, MakeDealEvent.Builder::withOrderDeal)
                .map(()-> 1, MakeDealEvent.Builder::withCounter)
                .build(MakeDealEvent.Builder::build);
    }

    public static OrderDeal map(OrderDealEntity orderDealEntity){
        return orderDealEntity != null ? Mapper.of(orderDealEntity, OrderDeal::builder)
                .map(OrderDealEntity::getId,OrderDeal.Builder::withId)
                .map(OrderDealEntity::getSsn,OrderDeal.Builder::withSsn)
                .map(OrderDealEntity::getOrderId,OrderDeal.Builder::withOrderId)
                .map(OrderDealEntity::getInstrument,OrderDeal.Builder::withInstrument)
                .map(OrderDealEntity::getNoOfItems,OrderDeal.Builder::withNoOfItems)
                .map(OrderDealEntity::getPrice,OrderDeal.Builder::withPrice)
                .build(OrderDeal.Builder::build) : null;
    }
}
