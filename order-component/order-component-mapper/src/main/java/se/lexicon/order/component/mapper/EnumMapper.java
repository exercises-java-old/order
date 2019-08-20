package se.lexicon.order.component.mapper;

import se.lexicon.order.component.domain.Side;

public class EnumMapper {

    public static se.lexicon.order.component.domain.OrderPriceType map(se.lexicon.market.component.domain.OrderPriceType price){
        if (price == se.lexicon.market.component.domain.OrderPriceType.MARKET)
            return se.lexicon.order.component.domain.OrderPriceType.MARKET;
        if (price == se.lexicon.market.component.domain.OrderPriceType.LIMIT)
            return se.lexicon.order.component.domain.OrderPriceType.LIMIT;
        //if (price == MarketPriceType.FULL_LIMIT)
        return se.lexicon.order.component.domain.OrderPriceType.FULL_LIMIT;
    }

    public static se.lexicon.market.component.domain.OrderPriceType map(se.lexicon.order.component.domain.OrderPriceType price){
        if (price == se.lexicon.order.component.domain.OrderPriceType.MARKET)
            return se.lexicon.market.component.domain.OrderPriceType.MARKET;
        if (price == se.lexicon.order.component.domain.OrderPriceType.LIMIT)
            return se.lexicon.market.component.domain.OrderPriceType.LIMIT;
        //if (price == OrderPriceType.FULL_LIMIT)
        return se.lexicon.market.component.domain.OrderPriceType.FULL_LIMIT;
    }

    public static Side map(se.lexicon.market.component.domain.Side side){
        if (side == se.lexicon.market.component.domain.Side.BUY)
            return Side.BUY;
        return Side.SELL;
    }

    public static se.lexicon.market.component.domain.Side map(Side side){
        if (side == Side.BUY)
            return se.lexicon.market.component.domain.Side.BUY;
        return se.lexicon.market.component.domain.Side.SELL;
    }

}
