package se.lexicon.order.component.service;

import com.so4it.gs.rpc.Component;
import com.so4it.gs.rpc.ServiceBindingType;


@Component(
        name = "checkout",
        serviceProviders = {
                OrderComponentServiceProvider.class
        },
        defaultServiceType = ServiceBindingType.GS_REMOTING
)
public class OrderComponentDescriptor {
}
