package se.lexicon.order.component.entity;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceFifoGroupingProperty;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.so4it.annotation.Allowed;
import com.so4it.common.util.object.Required;
import com.so4it.component.entity.AbstractEntityBuilder;
import com.so4it.component.entity.IdEntity;
import se.lexicon.order.component.domain.Money;

@SpaceClass
public class OrderDealEntity extends IdEntity<String> {

    @Allowed(value="Auto generated by GS",types = {Allowed.Type.NULLABLE})
    private String id;

    private String ssn;

    private String orderId;

    private String instrument;

    private Integer noOfItems;

    private Money price;

    private OrderDealEntity(){
    }
    
    private OrderDealEntity(Builder builder){
        this.id=builder.id;
        this.ssn =Required.notNull(builder.ssn,"ssn",builder.isTemplate());
        this.orderId =Required.notNull(builder.orderId,"orderId",builder.isTemplate());
        this.instrument= Required.notNull(builder.instrument,"instrument",builder.isTemplate());
        this.noOfItems= Required.notNull(builder.noOfItems,"noOfItems",builder.isTemplate());
        this.price =Required.notNull(builder.price,"price",builder.isTemplate());
    }


    @Override
    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }

    private void setId(String id){
        this.id =id;
    }

    @SpaceRouting
    public String getSsn(){
        return ssn;
    }

    private void setSsn(String ssn){
        this.ssn = ssn;
    }

    public String getOrderId(){
        return orderId;
    }

    private void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getInstrument(){
        return instrument;
    }

    private void setInstrument(String instrument){ this.instrument = instrument; }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    private void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public Money getPrice() { return price; }

    private void setPrice(Money price) { this.price = price; }

    public static Builder builder(){
        return new Builder(false);
    }

    public static Builder templateBuilder(){
        return new Builder(true);
    }

    public static class Builder extends AbstractEntityBuilder<OrderDealEntity> {

        private String id;
        private String ssn;
        private String orderId;
        private String instrument;
        private Integer noOfItems;
        private Money price;

        public Builder(boolean template){
            super(template);
        }

        public OrderDealEntity.Builder withId(String id){
            this.id=id;
            return this;
        }

        public OrderDealEntity.Builder withSsn(String ssn){
            this.ssn=ssn;
            return this;
        }

        public OrderDealEntity.Builder withOrderId(String orderId){
            this.orderId=orderId;
            return this;
        }

        public OrderDealEntity.Builder withInstrument(String instrument){
            this.instrument=instrument;
            return this;
        }

        public OrderDealEntity.Builder withNoOfItems(Integer noOfItems){
            this.noOfItems=noOfItems;
            return this;
        }

        public OrderDealEntity.Builder withPrice(Money price){
            this.price=price;
            return this;
        }

        @Override
        public OrderDealEntity build(){
            return new OrderDealEntity(this);
        }
    }
}
