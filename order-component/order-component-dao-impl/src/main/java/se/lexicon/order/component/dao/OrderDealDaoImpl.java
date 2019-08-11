package se.lexicon.order.component.dao;

import com.so4it.component.dao.gs.AbstractSpaceDao;
import org.openspaces.core.GigaSpace;
import se.lexicon.order.component.entity.OrderDealEntity;
import se.lexicon.order.componment.dao.OrderDealDao;

public class OrderDealDaoImpl extends AbstractSpaceDao<OrderDealEntity, String> implements OrderDealDao {

    public OrderDealDaoImpl(GigaSpace gigaSpace) {
        super(gigaSpace);
    }

}
