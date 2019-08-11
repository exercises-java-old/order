package se.lexicon.order.component.service;

import se.lexicon.order.component.domain.*;
import se.lexicon.order.component.entity.OrderBookEntity;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.gs.rpc.ServiceExport;
import se.lexicon.order.componment.dao.OrderBookDao;
import se.lexicon.order.componment.dao.OrderDao;
import se.lexicon.order.componment.dao.OrderDealDao;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ServiceExport({OrderComponentService.class})
public class OrderComponentServiceImpl implements OrderComponentService {

    private OrderDao orderDao;
    private OrderDealDao orderDealDao;

    public OrderComponentServiceImpl(OrderDao orderDao, OrderDealDao orderDealDao) {

        this.orderDao     = Required.notNull(orderDao,"orderDao");
        this.orderDealDao = Required.notNull(orderDealDao,"orderDealDao");
    }

    public Set<String> getInstruments(String ssn) {

        Map<String, String> instuments = new HashMap<>();
        orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).build()).stream()
                .forEach(item -> instuments.put(item.getInstrument(),item.getInstrument()));

        return instuments.values().stream().collect(Collectors.toSet());
    };

    @Override
    public Orders getOrders(String instrument, String ssn) {

        return Orders.valueOf(orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).withInstrument(instrument).build()).stream().
                map(entity -> Order.builder()
                        .withId(entity.getId())
                        .withSsn(ssn)
                        .withAmount(entity.getAmount())
                        .withInstrument(entity.getInstrument())
                        .withNoOfItems(entity.getNoOfItems())
                        .withMinMaxValue(entity.getMinMaxValue())
                        .withSide(entity.getSide())
                        .withInsertionTimestamp(entity.getInsertionTimestamp())
                        //.withOrderBookId(null)
                        .withOrderDealId(getOrderDeals(entity))
                        .build()).collect(Collectors.toSet()));
    }

    @Override
    public OrderDeals getOrderDeals (OrderEntity orderEntity) {

        OrderDeals orderDeals1 = OrderDeals.valueOf(orderDealDao.readAll(
                OrderDealEntity.templateBuilder().withOrderId1(orderEntity.getId()).build()).stream()
                .map(odentity -> OrderDeal.builder()
                        .withId(odentity.getId())
                        .withInstrument(odentity.getInstrument())
                        .withNoOfItems(odentity.getNoOfItems())
                        .withMatchingOrderId(odentity.getOrderId2())
                        .build())
                .collect(Collectors.toSet()));

        OrderDeals orderDeals2 = OrderDeals.valueOf(orderDealDao.readAll(
                OrderDealEntity.templateBuilder().withOrderId2(orderEntity.getId()).build()).stream()
                .map(odentity -> OrderDeal.builder()
                        .withId(odentity.getId())
                        .withInstrument(odentity.getInstrument())
                        .withNoOfItems(odentity.getNoOfItems())
                        .withMatchingOrderId(odentity.getOrderId1())
                        .build())
                .collect(Collectors.toSet()));

        return (orderDeals1.size() != 0) ? orderDeals1 : orderDeals2;

    }

//    @Override
//    public  OrderBooks getOrderBooks (OrderEntity orderEntity) {
//        return OrderBooks.valueOf(orderBookDao.readAll(
//            OrderBookEntity.templateBuilder().withSsn(orderEntity.getSsn()).withOrderId(orderEntity.getId()).build()).stream()
//            .map(obentity -> OrderBook.builder()
//                    .withId(obentity.getId())
//                    .withInstrument(obentity.getInstrument())
//                    .withNoOfItems(obentity.getNoOfItems())
//                    .withMinMaxValue(obentity.getMinMaxValue())
//                    .withSide(obentity.getSide())
//                    .withPhase(obentity.getPhase())
//                     .build())
//            .collect(Collectors.toSet()));
//    };

    @Override
    public void placeOrder(Order order) {

        MatchOrder (orderDao.insert(OrderEntity.builder()
                .withId(order.getId())
                .withSsn(order.getSsn())
                .withAmount(order.getAmount())
                .withInstrument(order.getInstrument())
                .withNoOfItems(order.getNoOfItems())
                .withMinMaxValue(order.getMinMaxValue())
                .withSide(order.getSide())
                .withInsertionTimestamp(order.getInsertionTimestamp())
                .withNoOfItemsToMatch(order.getNoOfItems())
                .withAllItemsMatched(false)
                .build()));

    }

    @Override
    public synchronized void MatchOrder (OrderEntity orderEntity) {


        // GET ALL ORDERS, FILTER AGAINST ALL OTHERS BUY/SELL with same Instrument and not fully matched
        Set<OrderEntity> orderEntities = orderDao.readAll
                (OrderEntity.templateBuilder()
                        .withSide(OtherSide(orderEntity.getSide()))
                        .withInstrument(orderEntity.getInstrument())
                        .withAllItemsMatched(false)
                        .build());

        double minMaxValue = 0d;

        boolean allPossibleMatchingFound = false;
        int noOfItemsToMatch = orderEntity.getNoOfItemsToMatch();
        int noOfItemsMatched = 0;

        while (!allPossibleMatchingFound) {

            OrderEntity bestMatchingOrder = null;

            for (OrderEntity matchingOrderEntity : orderEntities) {

                minMaxValue = AmountOf(matchingOrderEntity.getMinMaxValue().getAmount().doubleValue(),
                        matchingOrderEntity.getMinMaxValue().getCurrency(),
                        orderEntity.getMinMaxValue().getCurrency());

                if (orderEntity.getSide().equals(Side.SELL) ?
                        orderEntity.getMinMaxValue().getAmount().doubleValue() <= minMaxValue :
                        orderEntity.getMinMaxValue().getAmount().doubleValue() >= minMaxValue) {

                    bestMatchingOrder = chooseEntity
                            (noOfItemsToMatch, orderEntity.getMinMaxValue().getCurrency(),
                                    bestMatchingOrder,matchingOrderEntity);

                    if (bestMatchingOrder.getNoOfItemsToMatch().equals(noOfItemsToMatch) &&
                            bestMatchingOrder.getMinMaxValue().getCurrency().equals(orderEntity.getMinMaxValue().getCurrency()))
                        break; //Full matching found, exit loop
                }

            } // loop end;

           if (bestMatchingOrder == null){
                //System.out.println("No Match found for: " + orderEntity);
                return; //No matching found, exit this procedure
            }

            // Handle the result from the seach

            int itemsRemaining = noOfItemsToMatch - bestMatchingOrder.getNoOfItemsToMatch();
            allPossibleMatchingFound = itemsRemaining <= 0;
            noOfItemsMatched = itemsRemaining > 0 ? bestMatchingOrder.getNoOfItemsToMatch() : noOfItemsToMatch;

            orderEntity = orderDao.update(OrderEntity.builder()
                    .withId(orderEntity.getId())
                    .withSsn(orderEntity.getSsn())
                    .withAmount(orderEntity.getAmount())
                    .withInsertionTimestamp(orderEntity.getInsertionTimestamp())
                    .withNoOfItems(orderEntity.getNoOfItems())
                    .withSide(orderEntity.getSide())
                    .withMinMaxValue(orderEntity.getMinMaxValue())
                    .withInstrument(orderEntity.getInstrument())
                    .withNoOfItemsToMatch(orderEntity.getNoOfItemsToMatch() - noOfItemsMatched)
                    .withAllItemsMatched(itemsRemaining == 0)
                    .build());

            //System.out.println("Entity: " + orderEntity);

            OrderEntity orderedEntity = orderDao.update(OrderEntity.builder()
                    .withId(bestMatchingOrder.getId())
                    .withSsn(bestMatchingOrder.getSsn())
                    .withAmount(bestMatchingOrder.getAmount())
                    .withInsertionTimestamp(bestMatchingOrder.getInsertionTimestamp())
                    .withNoOfItems(bestMatchingOrder.getNoOfItems())
                    .withSide(bestMatchingOrder.getSide())
                    .withMinMaxValue(bestMatchingOrder.getMinMaxValue())
                    .withInstrument(bestMatchingOrder.getInstrument())
                    .withNoOfItemsToMatch(bestMatchingOrder.getNoOfItemsToMatch() - noOfItemsMatched)
                    .withAllItemsMatched((bestMatchingOrder.getNoOfItemsToMatch() - noOfItemsMatched) == 0)
                    .build());

            //System.out.println("Matched with: " + orderedEntity);

            // Create initial DEAL
            OrderDealEntity orderDealEntity = orderDealDao.insert(OrderDealEntity.builder()
                .withInstrument(orderEntity.getInstrument())
                .withNoOfItems(noOfItemsToMatch)
                .withOrderId1(orderEntity.getId())
                .withOrderId2(bestMatchingOrder.getId())
                .withClosed(false)
                .build());

            //System.out.println("Deal: " + orderDealEntity);

            orderEntities.remove(bestMatchingOrder); // Do not use this entity the next round

            noOfItemsToMatch = itemsRemaining;

        } // While loop
    }

    private double AmountOf(double amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) return amount;
        // NOT SAME CURRACY, CONVERT IT TO toCurrency
        return amount; // * ConvertionFactor(fromCurrency,toCurrency)
    }

    private Side OtherSide(Side side) {
        if (side == Side.BUY) return Side.SELL;
        return Side.BUY;
    }

    private OrderEntity chooseEntity
            (int noOfItemsToMatch, Currency inCurrency, OrderEntity current, OrderEntity compareWith) {

        if (current == null) return compareWith;

        if (current.getMinMaxValue().getCurrency().equals(compareWith.getMinMaxValue().getCurrency())) { // same currency

            if (current.getNoOfItemsToMatch().equals(noOfItemsToMatch)) return current;
            if (compareWith.getNoOfItemsToMatch().equals(noOfItemsToMatch)) return compareWith;
            if (current.getNoOfItemsToMatch() >= compareWith.getNoOfItemsToMatch()) return current;
            return compareWith;

        }

        // Otherwise, always choose the same currency as in the order
        if (inCurrency.equals(current.getMinMaxValue().getCurrency())) return current;
        return compareWith;

    }

    @Override
    public BigDecimal getTotalOrderValueOfAllOrders() { return orderDao.sum(); }

}
