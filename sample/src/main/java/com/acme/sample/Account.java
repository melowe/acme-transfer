package com.acme.sample;

import java.util.Objects;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class Account {

    private final AccountHolder accountHolder;

    private final String number;

    private final CurrencyUnit currency;

    private Account(AccountHolder accountHolder, String number, CurrencyUnit currency) {
        this.number = number;
        this.currency = currency;
        this.accountHolder = accountHolder;
    }

    public String getNumber() {
        return number;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.number);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        return Objects.equals(this.number, other.number);
    }

    public static class Builder {

        private AccountHolder accountHolder;

        private String number;

        private String currency;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder accountHolder(String accountHolder) {
            this.accountHolder = new AccountHolder(accountHolder);
            return this;
        }

        public Account build() {
            
            Objects.requireNonNull(accountHolder,"Account holder is required");
            Objects.requireNonNull(number,"Account number is required");
            Objects.requireNonNull(currency,"Account currency is required");

            final CurrencyUnit currencyUnit = Monetary.getCurrency(currency);

            return new Account(accountHolder, number, currencyUnit);
        }
    }

    @Override
    public String toString() {
        return "Account{" + "accountHolder=" + accountHolder + ", number=" + number + ", currency=" + currency + '}';
    }

}
