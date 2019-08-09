package se.lexicon.order.component.test.common.domain;

import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;
import se.lexicon.order.component.domain.Money;
import se.lexicon.order.component.domain.OrderBook;
import se.lexicon.order.component.domain.Phase;
import se.lexicon.order.component.domain.Side;

import java.math.BigDecimal;
import java.util.Currency;

public class OrderBookTestBuilder extends AbstractTestBuilder<OrderBook> {

    private OrderBook.Builder builder;

    public OrderBookTestBuilder(OrderBook.Builder builder){
        this.builder= Required.notNull(builder,"builder");
        this.builder
                 .withId("11111111")
                 .withInstrument("ABB")
                 .withNoOfItems(100)
                 .withSide(Side.BUY)
                 .withPhase(Phase.UNKNOWN)
                 .withMinMaxValue(Money.builder()
                                  .withAmount(BigDecimal.valueOf(400d))
                                  .withCurrency(Currency.getInstance("SEK"))
                                  .build())
                                 .build();

    }


    @Override
    public OrderBook build() {
        return null;
    }
}
