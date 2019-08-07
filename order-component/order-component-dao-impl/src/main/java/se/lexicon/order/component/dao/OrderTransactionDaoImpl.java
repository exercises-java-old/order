package se.lexicon.order.component.dao;

import com.j_spaces.core.client.SQLQuery;
import se.lexicon.order.component.entity.OrderTransactionEntity;
import se.lexicon.order.componment.dao.OrderTransactionDao;
import com.so4it.component.dao.gs.AbstractSpaceDao;
import org.openspaces.core.GigaSpace;

import static org.openspaces.extensions.QueryExtension.maxEntry;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class OrderTransactionDaoImpl extends AbstractSpaceDao<OrderTransactionEntity, String> implements OrderTransactionDao {



    public OrderTransactionDaoImpl(GigaSpace gigaSpace) {
        super(gigaSpace);
    }


    /**
     * Fetches the latest (e.g. highest insertion timestamp) {@code OrderTransactionEntity} available in the space.
     *
     *
     * @param arrangementId
     * @return
     */
    @Override
    public OrderTransactionEntity getLatest(String arrangementId) {
        SQLQuery<OrderTransactionEntity> sqlQuery = new SQLQuery<>(OrderTransactionEntity.class,"arrangementId = ?").setParameter(1,arrangementId);
        return maxEntry(getGigaSpace(),sqlQuery,"insertionTimestamp");
    }
}



