package com.acme.sample;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.money.MonetaryAmount;

public class Transfer  {

    private final LocalDateTime created;

    private final Account from;
    
    private final Account to;

    private final MonetaryAmount amount;

    private Transfer(LocalDateTime created, Account from, Account to, MonetaryAmount amount) {
        this.created = created;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public MonetaryAmount getAmount() {   
        return amount;
    }

    public static class Builder {

        private Account from;

        private Account to;

        private MonetaryAmount amount;

        private LocalDateTime created;

        public Builder from(Account from) {
            this.from = from;
            return this;
        }

        public Builder to(Account to) {
            this.to = to;
            return this;
        }

        public Builder amount(MonetaryAmount amount) {
            this.amount = amount;
            return this;
        }

        public Builder created(LocalDateTime created) {
            this.created = created;
            return this;
        }
        
        public Builder created() {
            this.created = LocalDateTime.now();
            return this;
        }

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Transfer build() {

            Objects.requireNonNull(from,"From account is required.");

            Objects.requireNonNull(to,"To account is required");

            Objects.requireNonNull(amount, "Amount is required");

            if (!Objects.equals(to.getCurrency(), amount.getCurrency())) {
                throw new InvalidTransferException(String.format("Cannot transfer %s to account %s. Account currency is %s", amount.getCurrency(), to.getNumber(), to.getCurrency()));
            }

            return new Transfer(created,from,to, amount);
        }

    }

    @Override
    public String toString() {
        return "Transaction{" + "created=" + created + ", from=" + from + ", to=" + to + ", amount=" + amount + '}';
    }

}
