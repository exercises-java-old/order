package se.lexicon.order.component.entity;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.so4it.annotation.Allowed;
import com.so4it.common.util.object.Required;
import com.so4it.component.entity.AbstractEntityBuilder;
import com.so4it.component.entity.IdEntity;

import java.util.Set;

@SpaceClass
public class OrderDealEntity extends IdEntity<String> {

    @Allowed(value="Auto generated by GS",types = {Allowed.Type.NULLABLE})
    private String id;

    private String instrument;

    private Integer noOfItems;

    private String orderId1;

    private String orderId2;

    private Boolean closed;

    private OrderDealEntity(){

    }
    
    private OrderDealEntity(Builder builder){
        this.id=builder.id;
        this.instrument= Required.notNull(builder.instrument,"instrument",builder.isTemplate());
        this.noOfItems= Required.notNull(builder.noOfItems,"noOfItems",builder.isTemplate());
        this.orderId1 =Required.notNull(builder.orderId1,"orderId1",builder.isTemplate());
        this.orderId2 =Required.notNull(builder.orderId2,"orderId2",builder.isTemplate());
        this.closed =Required.notNull(builder.closed,"closed",builder.isTemplate());
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
    public String getInstrument(){
        return instrument;
    }

    private void setInstrument(String instrument){
        this.instrument = instrument;

    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    private void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getOrderId1(){
        return orderId1;
    }

    private void setOrderId1(String orderId1){
        this.orderId1 = orderId1;
    }

    public String getOrderId2(){
        return orderId2;
    }

    private void setOrderId2(String orderId2){
        this.orderId2 = orderId2;
    }

    public Boolean getClosed() {
        return closed;
    }

    private void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public static Builder builder(){
        return new Builder(false);
    }

    public static Builder templateBuilder(){
        return new Builder(true);
    }

    public static class Builder extends AbstractEntityBuilder<OrderDealEntity> {

        private String id;
        private String instrument;
        private Integer noOfItems;
        private String orderId1;
        private String orderId2;
        private Boolean closed;

        public Builder(boolean template){
            super(template);
        }

        public OrderDealEntity.Builder withId(String id){
            this.id=id;
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

        public OrderDealEntity.Builder withOrderId1(String orderId1){
            this.orderId1=orderId1;
            return this;
        }

        public OrderDealEntity.Builder withOrderId2(String orderId2){
            this.orderId2=orderId2;
            return this;
        }

        public OrderDealEntity.Builder withClosed(Boolean closed){
            this.closed=closed;
            return this;
        }

        @Override
        public OrderDealEntity build(){
            return new OrderDealEntity(this);
        }
    }
}
