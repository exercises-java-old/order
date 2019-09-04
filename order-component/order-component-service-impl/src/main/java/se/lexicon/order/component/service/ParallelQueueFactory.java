package se.lexicon.order.component.service;

import com.j_spaces.core.client.SQLQuery;
import com.so4it.common.jmx.MBeanRegistry;
import com.so4it.common.util.object.Required;
import com.so4it.gs.queue.GigaSpaceParallelQueueConfiguration;
import com.so4it.gs.queue.GigaSpaceParallelQueueFactory;
import com.so4it.queue.ParallelQueue;
import org.openspaces.core.GigaSpace;
import org.springframework.transaction.PlatformTransactionManager;
import se.lexicon.order.component.event.MakeDealEvent;
import se.lexicon.order.component.event.PlaceOrderEvent;

public class ParallelQueueFactory {


    private final MBeanRegistry mBeanRegistry;

    private final GigaSpace gigaSpace;

    private final PlatformTransactionManager transactionManager;

    private final GigaSpaceParallelQueueConfiguration gigaSpaceParallelQueueConfiguration;

    public ParallelQueueFactory(GigaSpaceParallelQueueConfiguration gigaSpaceParallelQueueConfiguration, MBeanRegistry mBeanRegistry, GigaSpace gigaSpace, PlatformTransactionManager transactionManager) {
        this.mBeanRegistry = Required.notNull(mBeanRegistry, "mBeanRegistry");
        this.gigaSpace = Required.notNull(gigaSpace, "gigaSpace");
        this.transactionManager = Required.notNull(transactionManager, "transactionManager");
        this.gigaSpaceParallelQueueConfiguration = Required.notNull(gigaSpaceParallelQueueConfiguration, "gigaSpaceParallelQueueConfiguration");
    }

    public ParallelQueue<PlaceOrderEvent> createTaskExecutorParallelQueueOrders(OrderParallelQueueConsumer workflowParallelQueueConsumer) {
        return GigaSpaceParallelQueueFactory.createSingleReadSingleTakeWithFifoStaticTemplateWithTransactionManager(
                "OrderExecutor",
                gigaSpace,
                transactionManager,
                mBeanRegistry,
                gigaSpaceParallelQueueConfiguration,
                workflowParallelQueueConsumer,
                new SQLQuery<>(PlaceOrderEvent.class, ""));
    }

    public ParallelQueue<MakeDealEvent> createTaskExecutorParallelQueueDeals(OrderDealParallelQueueConsumer workflowParallelQueueConsumer) {
        return GigaSpaceParallelQueueFactory.createSingleReadSingleTakeWithFifoStaticTemplateWithTransactionManager(
                "OrderDealExecutor",
                gigaSpace,
                transactionManager,
                mBeanRegistry,
                gigaSpaceParallelQueueConfiguration,
                workflowParallelQueueConsumer,
                new SQLQuery<>(MakeDealEvent.class, ""));
    }

}
