package com.acme.sample;

import java.math.BigDecimal;
import java.util.Objects;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class ActualSampleService implements SampleService {

    private final Datastore datastore;

    public ActualSampleService(Datastore datastore) {
        this.datastore = Objects.requireNonNull(datastore);
    }

    @Override
    public void transfer(String from, String to, BigDecimal amount, String currency) {

        MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory()
                .setCurrency(currency)
                .setNumber(amount)
                .create();

        Account fromAccount = datastore.findAccountByNumber(from);
        Account toAccount = datastore.findAccountByNumber(to);

        Transfer transaction = Transfer.Builder.create()
        .from(fromAccount)
        .to(toAccount)
        .created()
        .amount(monetaryAmount)
        .build();

        datastore.storeTransaction(transaction);
    }


}
