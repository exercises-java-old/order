package se.lexicon.order.component.dao;

import com.j_spaces.core.client.SQLQuery;
import com.so4it.component.dao.gs.AbstractSpaceDao;
import org.openspaces.core.GigaSpace;
import org.openspaces.extensions.QueryExtension;
import se.lexicon.order.component.entity.OrderBookEntity;
import se.lexicon.order.component.entity.OrderEntity;
import se.lexicon.order.componment.dao.OrderBookDao;
import se.lexicon.order.componment.dao.OrderDao;

import java.math.BigDecimal;


/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class OrderBookDaoImpl extends AbstractSpaceDao<OrderBookEntity, String> implements OrderBookDao {

    public OrderBookDaoImpl(GigaSpace gigaSpace) {
        super(gigaSpace);
    }

//    @Override
//    public int sum() {
//        return QueryExtension.sum(getGigaSpace(),new SQLQuery<>(OrderEntity.class,""),"noofitems");
//    }

}



