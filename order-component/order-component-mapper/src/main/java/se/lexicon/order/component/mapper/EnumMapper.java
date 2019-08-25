package se.lexicon.order.component.mapper;

public class EnumMapper {

    public static se.lexicon.order.component.domain.OrderPriceType map(se.lexicon.market.component.domain.OrderPriceType price){
        if (price == se.lexicon.market.component.domain.OrderPriceType.MARKET)
            return se.lexicon.order.component.domain.OrderPriceType.MARKET;
        if (price == se.lexicon.market.component.domain.OrderPriceType.LIMIT)
            return se.lexicon.order.component.domain.OrderPriceType.LIMIT;

        return se.lexicon.order.component.domain.OrderPriceType.FULL_LIMIT;
    }

    public static se.lexicon.market.component.domain.OrderPriceType map(se.lexicon.order.component.domain.OrderPriceType price){
        if (price == se.lexicon.order.component.domain.OrderPriceType.MARKET)
            return se.lexicon.market.component.domain.OrderPriceType.MARKET;
        if (price == se.lexicon.order.component.domain.OrderPriceType.LIMIT)
            return se.lexicon.market.component.domain.OrderPriceType.LIMIT;

        return se.lexicon.market.component.domain.OrderPriceType.FULL_LIMIT;
    }

    public static se.lexicon.order.component.domain.Side map(se.lexicon.market.component.domain.Side side){
        if (side == se.lexicon.market.component.domain.Side.BUY)
            return se.lexicon.order.component.domain.Side.BUY;
        return se.lexicon.order.component.domain.Side.SELL;
    }

    public static se.lexicon.market.component.domain.Side map(se.lexicon.order.component.domain.Side side){
        if (side == se.lexicon.order.component.domain.Side.BUY)
            return se.lexicon.market.component.domain.Side.BUY;
        return se.lexicon.market.component.domain.Side.SELL;
    }

}
