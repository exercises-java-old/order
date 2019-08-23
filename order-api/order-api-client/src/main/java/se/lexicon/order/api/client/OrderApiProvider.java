package se.lexicon.order.api.client;

import com.so4it.api.Order;
import com.so4it.api.ApiClientProvider;

@ApiClientProvider(
        value = Order.NAME,
        specification = Order.PATH,
        version = Order.VERSION,
        beanPublisher = OrderApiProviderBeanPublisher.class)
public class OrderApiProvider {
}
