package se.lexicon.order.component.domain;

import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

public class Deal extends ValueObject{
    private String id;

    private String instrument;

    private String buyOrderId;

    private String sellOrderId;

    // private Set<Side> side;


    private Deal(){

    }

    public Deal(Builder builder) {
        this.id = Required.notNull(builder.id,"id");
        this.instrument = Required.notNull(builder.instrument,"instrument");
        this.sellOrderId = Required.notNull(builder.sellOrderId,"sellOrder");
        this.buyOrderId = Required.notNull(builder.buyOrderId,"buyOrder");
    }

    public String getInstrument() {
        return instrument;
    }

    private void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    private void setBuyOrderId(){
        this.buyOrderId = buyOrderId;
    }
    public String getBuyOrderId(){
        return buyOrderId;
    }
    private void setSellOrderId(){
        this.sellOrderId = sellOrderId;
    }
    private String getSellOrderId(){
        return sellOrderId;
    }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{id,buyOrderId,sellOrderId,instrument};
    }

    public static Builder  builder(){
        return new Builder();
    }

    public static class Builder implements com.so4it.common.builder.Builder<Deal> {

        private String id;

        private String instrument;

        private String buyOrderId;

        private String sellOrderId;

        @Override
        public Deal build() {
            return new Deal(this);
        }
    }
}
