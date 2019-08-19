package se.lexicon.order.component.domain;

import com.so4it.annotation.Allowed;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.io.Serializable;

public class OrderDeal extends ValueObject implements Serializable {

    private static final long serialVersionUID = 2L;

    @Allowed(types = {Allowed.Type.NULLABLE})
    private String id;

    private String ssn;

    private String orderId;

    private String instrument;

    private Integer noOfItems;

    private Money price;

    private OrderDeal() {
    }

    private OrderDeal(Builder builder) {
        this.id = builder.id;
        this.ssn = Required.notNull(builder.ssn,"ssn");
        this.orderId = Required.notNull(builder.orderId,"orderId");
        this.instrument = Required.notNull(builder.instrument,"instrument");
        this.noOfItems = Required.notNull(builder.noOfItems,"noOfItems");
        this.price = Required.notNull(builder.price,"price");
    }

    public String getId() {
        return id;
    }

    public String getSsn() {
        return ssn;
    }

    public String getInstrument() {
        return instrument;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public Money getPrice() { return price; }

    public String getOrderId() {
        return orderId;
    }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{id, ssn, orderId, instrument, noOfItems, price};
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements com.so4it.common.builder.Builder<OrderDeal>{

        private String id;

        private String ssn;

        private String orderId;

        private String instrument;

        private Integer noOfItems;

        private Money price;

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withSsn(String ssn){
            this.ssn = ssn;
            return this;
        }

        public Builder withOrderId(String orderId){
            this.orderId = orderId;
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

        public Builder withPrice(Money price){
            this.price = price;
            return this;
        }

        @Override
        public OrderDeal build() {
            return new OrderDeal(this);
        }
    }
}

