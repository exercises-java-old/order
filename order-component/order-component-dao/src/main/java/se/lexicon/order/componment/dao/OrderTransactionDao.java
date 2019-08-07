package se.lexicon.order.componment.dao;

import se.lexicon.order.component.entity.OrderTransactionEntity;
import com.so4it.component.GenericDao;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface OrderTransactionDao extends GenericDao<OrderTransactionEntity, String> {
    OrderTransactionEntity getLatest(String arrangementId);
}

