package se.lexicon.order.componment.dao;

import se.lexicon.order.component.entity.OrderEntity;
import com.so4it.component.GenericDao;

import java.math.BigDecimal;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface OrderDao extends GenericDao<OrderEntity, String> {

    BigDecimal sum();

}

