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

        MonetaryAmount senderBalance =  datastore.findBalance(fromAccount);

        Account toAccount = datastore.findAccountByNumber(to);
        
        Transfer transfer = Transfer.Builder.create()
            .from(fromAccount)
            .to(toAccount)
            .created()
            .amount(monetaryAmount)
            .build();

        if(senderBalance.isLessThan(monetaryAmount)) {
            throw new InSuffientFundsException("Insuffient funds to transfer");
        }
        
        datastore.executeTransfer(transfer);
    }

    @Override
    public MonetaryAmount findBalanceForAccountNumber(String number) {
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
