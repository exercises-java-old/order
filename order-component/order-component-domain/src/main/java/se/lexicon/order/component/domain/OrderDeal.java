package se.lexicon.order.component.domain;

import com.so4it.annotation.Allowed;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.io.Serializable;

public class OrderDeal extends ValueObject implements Serializable {

    private static final long serialVersionUID = 2L;

    @Allowed(types = {Allowed.Type.NULLABLE})
    private String id;

    private String instrument;

    private Integer noOfItems;

    private String matchingOrderId;

    private OrderDeal() {
    }

    private OrderDeal(Builder builder) {
        this.id = builder.id;
        this.instrument = Required.notNull(builder.instrument,"instrument");
        this.noOfItems = Required.notNull(builder.noOfItems,"noOfItems");
        this.matchingOrderId = Required.notNull(builder.matchingOrderId,"matchingOrderId");
    }

    public String getId() {
        return id;
    }

    public String getInstrument() {
        return instrument;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public String getMatchingOrderId() {
        return matchingOrderId;
    }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{id, instrument, noOfItems, matchingOrderId};
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements com.so4it.common.builder.Builder<OrderDeal>{

        private String id;

        private String instrument;

        private Integer noOfItems;

        private String matchingOrderId;

        public Builder withId(String id){
            this.id = id;
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

        public Builder withMatchingOrderId(String matchingOrderId){
            this.matchingOrderId = matchingOrderId;
            return this;
        }

        @Override
        public OrderDeal build() {
            return new OrderDeal(this);
        }
    }
}

