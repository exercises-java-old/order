package se.lexicon.order.component.service;

import se.lexicon.order.component.domain.*;
import se.lexicon.order.component.entity.OrderBookEntity;
import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.gs.rpc.ServiceExport;
import se.lexicon.order.componment.dao.OrderBookDao;
import se.lexicon.order.componment.dao.OrderDao;

import java.util.*;
import java.util.stream.Collectors;

@ServiceExport({OrderComponentService.class})
public class OrderComponentServiceImpl implements OrderComponentService {

    private OrderDao orderDao;
    private OrderBookDao orderBookDao;

    public OrderComponentServiceImpl(OrderDao orderDao, OrderBookDao orderBookDao) {

        this.orderDao     = Required.notNull(orderDao,"orderDao");
        this.orderBookDao = Required.notNull(orderBookDao,"orderBookDao");
    }

    @Override
    public Orders getOrders(String ssn) {

        //where ssn =  ssn
        //Set<OrderEntity> orderEntities = orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).build());

//        Set<OrderBookEntity> orderBookEntities =
//                orderBookDao.readAll(OrderBookEntity.templateBuilder().withSsn(ssn).build());
//
//        return Orders.valueOf(orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).build()).stream().
//                map(entity -> Order.builder()
//                        .withId(entity.getId())
//                        .withSsn(ssn)
//                        .withAmount(entity.getAmount())
//                        .withInsertionTimestamp(entity.getInsertionTimestamp())
//                        .withOrderBookId(OrderBooks.valueOf(orderBookEntities.stream()
//                                .filter(f -> f.getOrderId().equals(entity.getId()))
//                                .map(obentity -> OrderBook.builder()
//                                        .withId(obentity.getId())
//                                        .withInstrument(obentity.getInstrument())
//                                        .withMinMaxValue(obentity.getMinMaxValue())
//                                        .withPhase(obentity.getPhase())
//                                        .withSellOrder(obentity.getSellOrder())
//                                        .build())
//                                .collect(Collectors.toSet())))
//                        .build()).collect(Collectors.toSet()));

        return Orders.valueOf(orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).build()).stream().
                map(entity -> Order.builder()
                        .withId(entity.getId())
                        .withSsn(ssn)
                        .withAmount(entity.getAmount())
                        .withInstrument(entity.getInstrument())
                        .withNoOfItems(entity.getNoOfItems())
                        .withMinMaxValue(entity.getMinMaxValue())
                        .withSide(entity.getSide())
                        .withInsertionTimestamp(entity.getInsertionTimestamp())
                        .withOrderBookId(getOrderBooks(entity))
                        .build()).collect(Collectors.toSet()));
    }

    @Override
    public  OrderBooks getOrderBooks (OrderEntity orderEntity) {
        return OrderBooks.valueOf(orderBookDao.readAll(
            OrderBookEntity.templateBuilder().withSsn(orderEntity.getSsn()).withOrderId(orderEntity.getId()).build()).stream()
            .map(obentity -> OrderBook.builder()
                    .withId(obentity.getId())
                    .withInstrument(obentity.getInstrument())
                    .withNoOfItems(obentity.getNoOfItems())
                    .withMinMaxValue(obentity.getMinMaxValue())
                    .withSide(obentity.getSide())
                    .withPhase(obentity.getPhase())
                     .build())
            .collect(Collectors.toSet()));
    };

    @Override
    public void placeOrder(Order order) {

        OrderEntity orderEntity = orderDao.insert(OrderEntity.builder()
                .withId(order.getId())
                .withSsn(order.getSsn())
                .withAmount(order.getAmount())
                .withInstrument(order.getInstrument())
                .withNoOfItems(order.getNoOfItems())
                .withMinMaxValue(order.getMinMaxValue())
                .withSide(order.getSide())
                .withInsertionTimestamp(order.getInsertionTimestamp())
                .build());

//        order.getOrderBookId().asList().stream().map(orderBook -> OrderBookEntity.builder()
//                .withOrderId(order.getId())
//                .withSsn(order.getSsn())
//                .withInstrument(orderBook.getInstrument())
//                .withNoOfItems(orderBook.getNoOfItems())
//                .withMinMaxValue(orderBook.getMinMaxValue())
//                .withPhase(Phase.PENDING_INCOMING)
//                .withSellOrder(orderBook.getSellOrder())
//                .build())
//                .forEach(orderBookDao::insert);

        MatchOrder (orderEntity);

//        // Sweep through all orderBooks and try to match them against all corresponding orders
//        for (OrderBook orderBook : order.getOrderBooks()) {
//
//            if (orderBook.getPhase() == Phase.UNKNOWN || orderBook.getPhase() == Phase.PENDING_INCOMING) {
//
//                MatchOrder (orderEntity);
//
//            }
//
//        } // End of loop
    }

    @Override
    public void MatchOrder (OrderEntity orderEntity) {

        // GET ALL ORDERBOOKS, FILTER AGAINST ALL OTHERS BUY/SELL with same Instrument and Phase
        Set<OrderBookEntity> orderBookEntities = orderBookDao.readAll
                (OrderBookEntity.templateBuilder()
                        .withSide(OtherSide(orderEntity.getSide()))
                        .withInstrument(orderEntity.getInstrument())
                        .withPhase(Phase.PENDING_INCOMING)
                        .build());

        double minMaxValue = 0d;

        OrderBookEntity bestMatchingOrderBook = null;

        boolean allPossibleMatchingFound = false;
        int noOfItemsToMatch = orderEntity.getNoOfItems();
        int noOfItemsMatched = 0;

        while (!allPossibleMatchingFound) {

            for (OrderBookEntity orderBookEntity : orderBookEntities) {
//                if (orderBookEntity.getPhase() == Phase.UNKNOWN ||
//                        orderBookEntity.getPhase() == Phase.PENDING_INCOMING) {

                minMaxValue = AmountOf(orderBookEntity.getMinMaxValue().getAmount().doubleValue(),
                        orderBookEntity.getMinMaxValue().getCurrency(),
                        orderEntity.getMinMaxValue().getCurrency());

                if (orderEntity.getSide().equals(Side.SELL) ?
                        orderEntity.getMinMaxValue().getAmount().doubleValue() <= minMaxValue :
                        orderEntity.getMinMaxValue().getAmount().doubleValue() >= minMaxValue) {

                    bestMatchingOrderBook = chooseEntity
                            (noOfItemsToMatch, orderEntity.getMinMaxValue().getCurrency(),
                                    bestMatchingOrderBook,orderBookEntity);

                    if (bestMatchingOrderBook.getNoOfItems().equals(noOfItemsToMatch) &&
                            bestMatchingOrderBook.getMinMaxValue().getCurrency().equals(orderEntity.getMinMaxValue().getCurrency()))
                        break; //Full matching found, exit loop
                }
//                }
            } // loop end;

           if (bestMatchingOrderBook == null){

                // No matching, enter in Dao for later use
                OrderBookEntity orderBookEntity = orderBookDao.insert(OrderBookEntity.builder()
                        //.withId(orderBook.getId())
                        .withSsn(orderEntity.getSsn())
                        .withOrderId(orderEntity.getId())
                        .withNoOfItems(noOfItemsToMatch)
                        .withPhase(Phase.PENDING_INCOMING)
                        .withSide(orderEntity.getSide())
                        .withMinMaxValue(orderEntity.getMinMaxValue())
                        .withInstrument(orderEntity.getInstrument())
                        //.withMatchingOrderId() NO Matching order exists
                        .build());

                //System.out.println("Stored: " + orderBookEntity);

                return; //No matching found, exit this procedure
            }

            // Handle the result from the seach

            int itemsRemaining = noOfItemsToMatch - bestMatchingOrderBook.getNoOfItems();
            allPossibleMatchingFound = itemsRemaining <= 0;
            noOfItemsMatched = itemsRemaining > 0 ? bestMatchingOrderBook.getNoOfItems() : noOfItemsToMatch;

            OrderBookEntity orderBookEntity = orderBookDao.insert(OrderBookEntity.builder()
                    //.withId(orderBook.getId())
                    .withSsn(orderEntity.getSsn())
                    .withOrderId(orderEntity.getId())
                    .withNoOfItems(noOfItemsMatched)
                    .withPhase(Phase.PENDING_OUTGOING)
                    .withSide(orderEntity.getSide())
                    .withMinMaxValue(orderEntity.getMinMaxValue())
                    .withInstrument(orderEntity.getInstrument())
                    .withMatchingOrderId(bestMatchingOrderBook.getOrderId())
                    .build());

            //System.out.println("Stored: " + orderBookEntity);

            orderBookEntity = orderBookDao.insertOrUpdate(OrderBookEntity.builder()
                    .withId(bestMatchingOrderBook.getId())
                    .withSsn(bestMatchingOrderBook.getSsn())
                    .withOrderId(bestMatchingOrderBook.getOrderId())
                    .withNoOfItems(noOfItemsMatched)
                    .withPhase(Phase.PENDING_OUTGOING)
                    .withSide(bestMatchingOrderBook.getSide())
                    .withMinMaxValue(bestMatchingOrderBook.getMinMaxValue())
                    .withInstrument(bestMatchingOrderBook.getInstrument())
                    .withMatchingOrderId(orderEntity.getId())
                    .build());

           //System.out.println("Matched with: " + orderBookEntity);

            if (itemsRemaining < 0) {
                // Insert the remaining matching items
                orderBookEntity = orderBookDao.insert(OrderBookEntity.builder()
                        .withSsn(bestMatchingOrderBook.getSsn())
                        .withOrderId(bestMatchingOrderBook.getId())
                        .withNoOfItems(Math.abs(itemsRemaining))
                        .withPhase(bestMatchingOrderBook.getPhase())
                        .withSide(bestMatchingOrderBook.getSide())
                        .withMinMaxValue(bestMatchingOrderBook.getMinMaxValue())
                        .withInstrument(bestMatchingOrderBook.getInstrument())
                        .build());

                //System.out.println("Remainder: " + orderBookEntity);

                return; //All possible matchings found, exit this procedure

            } else {noOfItemsToMatch = itemsRemaining;} // Continue with the rest

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

    private OrderBookEntity chooseEntity
            (int noOfItemsToMatch, Currency inCurrency, OrderBookEntity current, OrderBookEntity compareWith) {

        if (current.getMinMaxValue().getCurrency().equals(compareWith.getMinMaxValue().getCurrency())) { // same currency

            if (current.getNoOfItems().equals(noOfItemsToMatch)) return current;
            if (compareWith.getNoOfItems().equals(noOfItemsToMatch)) return compareWith;
            if (current.getNoOfItems() >= compareWith.getNoOfItems()) return current;
            return compareWith;

        }

        // Otherwise, always choose the same currency as in the order
        if (inCurrency.equals(current.getMinMaxValue().getCurrency())) return current;
        return compareWith;

    }

//    @Override
//    public BigDecimal getTotalOrderValueOfAllOrders() { return orderDao.sum(); }

}
