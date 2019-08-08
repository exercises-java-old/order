package se.lexicon.order.component.service;

import com.so4it.gs.rpc.Service;
import com.so4it.gs.rpc.ServiceBindingType;
import com.so4it.gs.rpc.ServiceProvider;

@ServiceProvider
public interface OrderComponentServiceProvider {

    @Service(value = ServiceBindingType.GS_REMOTING, name = OrderComponentService.DEFAULT_BEAN_NAME)
    OrderComponentService getOrderComponentService();
}
