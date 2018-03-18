package com.acme.sample;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

public class MemoryDatastoreTest {

    Account fromAccount = Account.Builder.create()
            .number("133246672")
            .currency("TMM")
            .accountHolder("SomeOne")
            .build();

    Account toAccount = Account.Builder.create()
            .number("28899202")
            .currency("TMM")
            .accountHolder("SomeOneElse")
            .build();

    @Test
    public void executeTransfer() {

        ConcurrentMap<Account, MonetaryAmount> balances = new ConcurrentHashMap<>();
        balances.put(fromAccount,
                Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(1000)
                        .create());

        balances.put(toAccount,
                Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(0)
                        .create());

        MemoryDatastore datastore = new MemoryDatastore(balances);

        MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                .setCurrency("TMM")
                .setNumber(1000)
                .create();

        Transfer transfer = Transfer.Builder.create()
                .created()
                .from(fromAccount)
                .to(toAccount)
                .amount(amount)
                .build();

        datastore.executeTransfer(transfer);

        assertThat(balances).hasSize(2)
                .containsOnlyKeys(fromAccount, toAccount);

        assertThat(balances.get(fromAccount))
                .isEqualByComparingTo(Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(0)
                        .create());

        assertThat(balances.get(toAccount))
                .isEqualByComparingTo(Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(1000)
                        .create());
    }

        @Test
    public void executeTransferInsuffientFunds() {

        ConcurrentMap<Account, MonetaryAmount> balances = new ConcurrentHashMap<>();
        balances.put(fromAccount,
                Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(1)
                        .create());

        balances.put(toAccount,
                Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(100)
                        .create());

        MemoryDatastore datastore = new MemoryDatastore(balances);

        MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                .setCurrency("TMM")
                .setNumber(2)
                .create();

        Transfer transfer = Transfer.Builder.create()
                .created()
                .from(fromAccount)
                .to(toAccount)
                .amount(amount)
                .build();

        try {
            datastore.executeTransfer(transfer);
            failBecauseExceptionWasNotThrown(InSuffientFundsException.class);
        } catch(InSuffientFundsException ex) {
            assertThat(ex).hasMessageContaining(fromAccount.getNumber());
        }
        assertThat(balances).hasSize(2)
                .containsOnlyKeys(fromAccount, toAccount);

        assertThat(balances.get(fromAccount))
                .isEqualByComparingTo(Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(1)
                        .create());

        assertThat(balances.get(toAccount))
                .isEqualByComparingTo(Monetary.getDefaultAmountFactory()
                        .setCurrency("TMM")
                        .setNumber(100)
                        .create());
    }
    
}
