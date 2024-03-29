package se.lexicon.order.component.domain;

import com.so4it.annotation.Allowed;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class Order extends ValueObject implements Serializable {

    private static final long serialVersionUID = 2L;

    @Allowed(types = {Allowed.Type.NULLABLE})
    private String id;

    private String ssn;

    private BigDecimal amount;

    // Timestamp from core system when the order was made
    private Instant insertionTimestamp;

    // original order
    private String instrument;

    private Integer noOfItems;

    private Money minMaxValue; //Money minValue if SELL, maxValue if BUY;

    private Side side;         // Sell or Buy

    private OrderPriceType orderPriceType; // BUY/SELL at MARKET or LIMITED price

    private String orderBookId;

    @Allowed(types = {Allowed.Type.NULLABLE})
    private OrderDeals orderDeals; //reference to the orderdeals

    private Order() {
    }

    private Order(Builder builder) {
        this.id = builder.id;
        this.ssn = Required.notNull(builder.ssn,"ssn");
        this.amount = Required.notNull(builder.amount,"amount");
        this.instrument = Required.notNull(builder.instrument,"instrument");
        this.noOfItems = Required.notNull(builder.noOfItems,"noOfItems");
        this.minMaxValue = Required.notNull(builder.minMaxValue,"minMaxValue");
        this.side = Required.notNull(builder.side,"side");
        this.orderPriceType = Required.notNull(builder.orderPriceType,"orderPriceType");
        this.insertionTimestamp = Required.notNull(builder.insertionTimestamp,"insertionTimestamp");
        this.orderBookId = Required.notNull(builder.orderBookId,"orderBookId");
        this.orderDeals = builder.orderDeals;
    }


    public String getId() {
        return id;
    }

    public String getSsn() {
        return ssn;
    }

    public String getOrderBookId() {
        return orderBookId;
    }

    public OrderDeals getOrderDeals() { return orderDeals; }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getInsertionTimestamp() { return insertionTimestamp; }

    public String getInstrument() {
        return instrument;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public Money getMinMaxValue() {
        return minMaxValue;
    }

    public Side getSide() {
        return side;
    }

    public OrderPriceType getOrderPriceType() { return orderPriceType; }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{id, ssn, amount, insertionTimestamp, instrument, noOfItems, minMaxValue, side, orderPriceType, orderBookId};
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements com.so4it.common.builder.Builder<Order>{

        private String id;

        private String ssn;

        private BigDecimal amount;

        private String instrument;

        private Integer noOfItems;

        private Money minMaxValue;

        private Side side;

        private OrderPriceType orderPriceType;

        private String orderBookId;

        private OrderDeals orderDeals;

        private Instant insertionTimestamp;

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withSsn(String ssn){
            this.ssn = ssn;
            return this;
        }

        public Builder withAmount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public Builder withInstrument(String instrument){
            this.instrument = instrument;
            return this;
        }

        public Builder withNoOfItems(Integer noOfItems){
            this.noOfItems = noOfItems;
            return this;
        }

        public Builder withMinMaxValue(Money minMaxValue){
            this.minMaxValue = minMaxValue;
            return this;
        }

        public Builder withSide(Side side){
            this.side = side;
            return this;
        }

        public Builder withOrderPriceType(OrderPriceType orderPriceType){
            this.orderPriceType = orderPriceType;
            return this;
        }

        public Builder withInsertionTimestamp(Instant insertionTimestamp){
            this.insertionTimestamp = insertionTimestamp;
            return this;
        }

        public Builder withOrderBookId(String orderBookId){
            this.orderBookId = orderBookId;
            return this;
        }

        public Builder withOrderDealId(OrderDeals orderDealId){
            this.orderDeals = orderDealId;
            return this;
        }

        @Override
        public Order build() {
            return new Order(this);
        }
    }
}
