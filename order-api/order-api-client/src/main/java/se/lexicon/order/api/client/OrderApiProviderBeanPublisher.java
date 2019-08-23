package se.lexicon.order.api.client;

import com.so4it.api.AbstractApiClientProviderBeanPublisher;
import com.so4it.api.ApiFrameworkConfiguration;
import com.so4it.api.importer.ApiClientProviderDefinition;
import com.so4it.api.util.StatusRuntimeExceptionBeanProxy;
import com.so4it.common.bean.BeanContext;
import com.so4it.common.bean.BeanProxyInvocationHandler;
//import com.so4it.ft.core.FaultTolerantBeanProxy;
import com.so4it.metric.springframework.MetricsTimerBeanProxy;
import com.so4it.request.core.RequestContextBeanProxy;
import io.grpc.ManagedChannel;
import se.lexicon.order.OrderApiServiceGrpc;

public class OrderApiProviderBeanPublisher extends AbstractApiClientProviderBeanPublisher {

    @Override
    public void publish(ApiClientProviderDefinition apiProviderDefinition,
                        BeanContext beanContext,
                        ApiFrameworkConfiguration configuration,
                        ManagedChannel managedChannel) {
        OrderApiServiceGrpc.OrderApiServiceBlockingStub agreementApiServiceBlockingStub = OrderApiServiceGrpc.newBlockingStub(managedChannel);
        OrderApiClient orderApiClientImpl = new OrderApiClientImpl(agreementApiServiceBlockingStub);
        OrderApiClient orderApiClient = BeanProxyInvocationHandler.create(
                OrderApiClient.class,
                orderApiClientImpl,
                StatusRuntimeExceptionBeanProxy.create(),
                MetricsTimerBeanProxy.create(orderApiClientImpl),
                //FaultTolerantBeanProxy.create(orderApiClientImpl, beanContext),
                RequestContextBeanProxy.create());
        beanContext.register(OrderApiClient.DEFAULT_API_BEAN_NAME, orderApiClient);
    }

}
