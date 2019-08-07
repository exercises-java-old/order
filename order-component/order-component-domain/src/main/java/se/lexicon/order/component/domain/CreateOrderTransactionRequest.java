package se.lexicon.order.component.domain;

import com.so4it.common.domain.DomainClass;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@DomainClass
public class CreateOrderTransactionRequest extends ValueObject {

    private String arrangementId;

    private String batchId;

    private Instant insertionTimestamp;

    private String address;

    private Phase phase;

    private Money amount;


    private CreateOrderTransactionRequest() {
    }

    private CreateOrderTransactionRequest(Builder builder) {
        this.arrangementId = Required.notNull(builder.arrangementId,"arrangementId");
        this.batchId = Required.notNull(builder.batchId,"batchId");
        this.insertionTimestamp = Required.notNull(builder.insertionTimestamp,"insertionTimestamp");
        this.address = Required.notNull(builder.address,"address");
        this.phase = Required.notNull(builder.phase,"phase");
        this.amount = Required.notNull(builder.amount,"amount");
    }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{arrangementId,batchId, insertionTimestamp, address, phase, amount};
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

    public static class Builder implements com.so4it.common.builder.Builder<CreateOrderTransactionRequest>{

        private String arrangementId;

        private String batchId;

        private Instant insertionTimestamp;

        private String address;

        private Phase phase;

        private Money amount;

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
        public CreateOrderTransactionRequest build() {
            return new CreateOrderTransactionRequest(this);
        }
    }


}
