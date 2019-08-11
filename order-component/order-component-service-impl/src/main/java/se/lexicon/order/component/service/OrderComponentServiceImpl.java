package se.lexicon.order.component.service;

import se.lexicon.order.component.domain.*;
import se.lexicon.order.component.entity.OrderBookEntity;
import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.gs.rpc.ServiceExport;
import se.lexicon.order.componment.dao.OrderBookDao;
import se.lexicon.order.componment.dao.OrderDao;

import java.math.BigDecimal;
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

    public Set<String> getInstruments(String ssn) {

        Map<String, String> instuments = new HashMap<>();
        orderDao.readAll(OrderEntity.templateBuilder().withSsn(ssn).build()).stream()
                .forEach(item -> instuments.put(item.getInstrument(),item.getInstrument()));

        return instuments.values().stream().collect(Collectors.toSet());
    };

    @Override
    public Orders getOrders(String instrument, String ssn) {

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

//        OrderEntity orderEntity = orderDao.insert(OrderEntity.builder()
//                .withId(order.getId())
//                .withSsn(order.getSsn())
//                .withAmount(order.getAmount())
//                .withInstrument(order.getInstrument())
//                .withNoOfItems(order.getNoOfItems())
//                .withMinMaxValue(order.getMinMaxValue())
//                .withSide(order.getSide())
//                .withInsertionTimestamp(order.getInsertionTimestamp())
//                .build());

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
    public synchronized void MatchOrder (OrderEntity orderEntity) {


        // GET ALL ORDERS, FILTER AGAINST ALL OTHERS BUY/SELL with same Instrument and not fully matched
        Set<OrderEntity> orderEntities = orderDao.readAll
                (OrderEntity.templateBuilder()
                        .withSide(OtherSide(orderEntity.getSide()))
                        .withInstrument(orderEntity.getInstrument())
                        .withAllItemsMatched(false)
                        .build());

        double minMaxValue = 0d;

        OrderEntity bestMatchingOrder = null;

        boolean allPossibleMatchingFound = false;
        int noOfItemsToMatch = orderEntity.getNoOfItemsToMatch();
        int noOfItemsMatched = 0;

        while (!allPossibleMatchingFound) {

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

                    if (bestMatchingOrder.getNoOfItems().equals(noOfItemsToMatch) &&
                            bestMatchingOrder.getMinMaxValue().getCurrency().equals(orderEntity.getMinMaxValue().getCurrency()))
                        break; //Full matching found, exit loop
                }

            } // loop end;

           if (bestMatchingOrder == null){

                // No matching, enter in Dao for later use
//                OrderBookEntity orderBookEntity = orderBookDao.insert(OrderBookEntity.builder()
//                        //.withId(orderBook.getId())
//                        .withSsn(orderEntity.getSsn())
//                        .withOrderId(orderEntity.getId())
//                        .withNoOfItems(noOfItemsToMatch)
//                        .withPhase(Phase.PENDING_INCOMING)
//                        .withSide(orderEntity.getSide())
//                        .withMinMaxValue(orderEntity.getMinMaxValue())
//                        .withInstrument(orderEntity.getInstrument())
//                        //.withMatchingOrderId() NO Matching order exists
//                        .build());

                //System.out.println("Stored: " + orderBookEntity);

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

            //System.out.println("Stored: " + orderEntity);

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

            // Create initial DEAL

           //System.out.println("Matched with: " + orderedEntity);

            noOfItemsToMatch = itemsRemaining;

//            if (itemsRemaining < 0) {
//                // Insert the remaining matching items
//                orderBookEntity = orderBookDao.insert(OrderBookEntity.builder()
//                        .withSsn(bestMatchingOrderBook.getSsn())
//                        .withOrderId(bestMatchingOrderBook.getId())
//                        .withNoOfItems(Math.abs(itemsRemaining))
//                        .withPhase(Phase.PENDING_INCOMING)
//                        .withSide(bestMatchingOrderBook.getSide())
//                        .withMinMaxValue(bestMatchingOrderBook.getMinMaxValue())
//                        .withInstrument(bestMatchingOrderBook.getInstrument())
//                        .build());
//
//                //System.out.println("Remainder: " + orderBookEntity);
//
//                return; //All possible matchings found, exit this procedure
//
//            } else {noOfItemsToMatch = itemsRemaining;} // Continue with the rest

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
