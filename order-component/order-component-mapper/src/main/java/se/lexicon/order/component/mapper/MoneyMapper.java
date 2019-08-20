package se.lexicon.order.component.mapper;

import com.so4it.common.util.Mapper;
//import se.lexicon.account.component.domain.Money;
//import se.lexicon.order.component.domain.Money;
//import se.lexicon.market.component.domain.Money;

public final class MoneyMapper {

    public static se.lexicon.order.component.domain.Money map(se.lexicon.account.component.domain.Money money){
        return Mapper.of(money, se.lexicon.order.component.domain.Money::builder)
                .map(se.lexicon.account.component.domain.Money::getAmount,se.lexicon.order.component.domain.Money.Builder::withAmount)
                .map(se.lexicon.account.component.domain.Money::getCurrency,se.lexicon.order.component.domain.Money.Builder::withCurrency)
                .build(se.lexicon.order.component.domain.Money.Builder::build);
    }

    public static se.lexicon.account.component.domain.Money map(se.lexicon.order.component.domain.Money money){
        return Mapper.of(money, se.lexicon.account.component.domain.Money::builder)
                .map(se.lexicon.order.component.domain.Money::getAmount,se.lexicon.account.component.domain.Money.Builder::withAmount)
                .map(se.lexicon.order.component.domain.Money::getCurrency,se.lexicon.account.component.domain.Money.Builder::withCurrency)
                .build(se.lexicon.account.component.domain.Money.Builder::build);
    }

    public static se.lexicon.order.component.domain.Money map(se.lexicon.market.component.domain.Money money){
        return Mapper.of(money, se.lexicon.order.component.domain.Money::builder)
                .map(se.lexicon.market.component.domain.Money::getAmount,se.lexicon.order.component.domain.Money.Builder::withAmount)
                .map(se.lexicon.market.component.domain.Money::getCurrency,se.lexicon.order.component.domain.Money.Builder::withCurrency)
                .build(se.lexicon.order.component.domain.Money.Builder::build);
    }

    public static se.lexicon.market.component.domain.Money mapIt(se.lexicon.order.component.domain.Money money){
        return Mapper.of(money, se.lexicon.market.component.domain.Money::builder)
                .map(se.lexicon.order.component.domain.Money::getAmount,se.lexicon.market.component.domain.Money.Builder::withAmount)
                .map(se.lexicon.order.component.domain.Money::getCurrency,se.lexicon.market.component.domain.Money.Builder::withCurrency)
                .build(se.lexicon.market.component.domain.Money.Builder::build);
    }

}
