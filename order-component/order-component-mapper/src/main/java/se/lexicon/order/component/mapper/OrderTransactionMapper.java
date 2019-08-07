package se.lexicon.order.component.mapper;

import se.lexicon.order.component.entity.OrderTransactionEntity;
import se.lexicon.order.component.domain.OrderTransaction;
import se.lexicon.order.component.domain.CreateOrderTransactionRequest;
import com.so4it.common.util.Mapper;
import se.lexicon.order.component.entity.OrderTransactionEntity;


public final class OrderTransactionMapper {



    public static OrderTransactionEntity map(CreateOrderTransactionRequest createOrderTransactionRequest){
        return Mapper.of(createOrderTransactionRequest, OrderTransactionEntity::builder)
                .map(CreateOrderTransactionRequest::getArrangementId,OrderTransactionEntity.Builder::withArrangementId)
                .map(CreateOrderTransactionRequest::getBatchId,OrderTransactionEntity.Builder::withBatchId)
                .map(CreateOrderTransactionRequest::getInsertionTimestamp,OrderTransactionEntity.Builder::withInsertionTimestamp)
                .map(CreateOrderTransactionRequest::getAddress,OrderTransactionEntity.Builder::withAddress)
                .map(CreateOrderTransactionRequest::getPhase,OrderTransactionEntity.Builder::withPhase)
                .map(CreateOrderTransactionRequest::getAmount, OrderTransactionEntity.Builder::withAmount)
                .build(OrderTransactionEntity.Builder::build);
    }


    public static OrderTransaction map(OrderTransactionEntity entity){
        return entity != null ? Mapper.of(entity, OrderTransaction::builder)
                .map(OrderTransactionEntity::getId,OrderTransaction.Builder::withId)
                .map(OrderTransactionEntity::getArrangementId,OrderTransaction.Builder::withArrangementId)
                .map(OrderTransactionEntity::getBatchId,OrderTransaction.Builder::withBatchId)
                .map(OrderTransactionEntity::getInsertionTimestamp,OrderTransaction.Builder::withInsertionTimestamp)
                .map(OrderTransactionEntity::getAddress,OrderTransaction.Builder::withAddress)
                .map(OrderTransactionEntity::getPhase,OrderTransaction.Builder::withPhase)
                .map(OrderTransactionEntity::getAmount,OrderTransaction.Builder::withAmount)
                .build(OrderTransaction.Builder::build) : null;
    }
}
