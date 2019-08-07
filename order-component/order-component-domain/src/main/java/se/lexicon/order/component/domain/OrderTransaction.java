package se.lexicon.order.component.domain;

import com.so4it.common.domain.DomainClass;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.time.Instant;

@DomainClass
public class OrderTransaction extends ValueObject {

    private String id;

    private String arrangementId;

    private String batchId;

    private Instant insertionTimestamp;

    private String address;

    private Phase phase;

    private Money amount;


    private OrderTransaction() {
    }

    private OrderTransaction(Builder builder) {
        this.id = Required.notNull(builder.id,"id");
        this.arrangementId = Required.notNull(builder.arrangementId,"arrangementId");
        this.batchId = Required.notNull(builder.batchId,"batchId");
        this.insertionTimestamp = Required.notNull(builder.insertionTimestamp,"insertionTimestamp");
        this.address = Required.notNull(builder.address,"address");
        this.phase = Required.notNull(builder.phase,"phase");
        this.amount = Required.notNull(builder.amount,"amount");
    }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{id, arrangementId, batchId, insertionTimestamp, address, phase, amount};
    }


    public String getId() {
        return id;
    }

    public String getArrangementId() {
        return arrangementId;
    }

    public String getBatchId() {
        return batchId;
    }

    public Instant getInsertionTimestamp() { return insertionTimestamp; }

    public String getAddress() { return address; }

    public Phase getPhase() { return phase; }

    public Money getAmount() { return amount; }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements com.so4it.common.builder.Builder<OrderTransaction>{

        private String id;

        private String arrangementId;

        private String batchId;

        private Instant insertionTimestamp;

        private String address;

        private Phase phase;

        private Money amount;

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withArrangementId(String arrangementId){
            this.arrangementId = arrangementId;
            return this;
        }

        public Builder withBatchId(String batchId){
            this.batchId = batchId;
            return this;
        }

        public Builder withInsertionTimestamp(Instant insertionTimestamp){
            this.insertionTimestamp = insertionTimestamp;
            return this;
        }

        public Builder withAddress(String address){
            this.address = address;
            return this;
        }

        public Builder withPhase(Phase phase){
            this.phase = phase;
            return this;
        }

        public Builder withAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public OrderTransaction build() {
            return new OrderTransaction(this);
        }
    }


}
