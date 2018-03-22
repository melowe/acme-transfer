package com.acme.sample;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class MemoryDatastoreStressIT {

    private static final int THREADS = 100;

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(THREADS);

    private static final Account ACCOUNT1 = Account.Builder.create()
            .number("1342672")
            .currency("GBP")
            .accountHolder("HOLDER1")
            .build();

    private static final Account ACCOUNT2 = Account.Builder.create()
            .number("2882882")
            .currency("GBP")
            .accountHolder("HOLDER1")
            .build();

    private MemoryDatastore memoryDatastore;

    private final MonetaryAmountFactory monetaryAmountFactory = Monetary.getDefaultAmountFactory();

    private final Map<Account, MonetaryAmount> dataMap = buildAccountData();

    public MemoryDatastoreStressIT() {
    }

    private ExecutorService executorService;

    @Before
    public void setUp() {
        memoryDatastore = new MemoryDatastore(dataMap);
    }

    @Test
    public void writeTransfers() throws Exception {

        executorService = Executors.newFixedThreadPool(THREADS);
        List<Account> accounts = Arrays.asList(ACCOUNT1, ACCOUNT2);
        List<TransferThread> transfers = IntStream.range(0, THREADS)
                .mapToObj(i -> {

                    Collections.reverse(accounts);
                    Account fromAccount = accounts.get(0);

                    Account toAccount = accounts.get(1);

                    MonetaryAmount amount = monetaryAmountFactory
                            .setCurrency("GBP")
                            .setNumber(120.08).create();

                    return Transfer.Builder.create()
                            .from(fromAccount).to(toAccount).created().amount(amount).build();

                }).map(t -> new TransferThread(t, memoryDatastore))
                .collect(Collectors.toList());

        executorService.invokeAll(transfers);

        executorService.shutdown();

        assertThat(COUNT_DOWN_LATCH.await(THREADS, TimeUnit.SECONDS)).isTrue();

        assertThat(dataMap.get(ACCOUNT1).getNumber().doubleValue())
                .isEqualTo(10000.00);

        assertThat(dataMap.get(ACCOUNT2).getNumber().doubleValue())
                .isEqualTo(20000.00);

    }

    static class TransferThread implements Callable<Void> {

        private final Transfer transfer;

        private final MemoryDatastore datastore;

        public TransferThread(Transfer transfer, MemoryDatastore datastore) {
            this.transfer = transfer;
            this.datastore = datastore;
        }

        @Override
        public Void call() throws Exception {

            datastore.executeTransfer(transfer);
            COUNT_DOWN_LATCH.countDown();

            return null;
        }

    }

    public static Map<Account, MonetaryAmount> buildAccountData() {
        List<Account> someAccounts = Arrays.asList(ACCOUNT1, ACCOUNT2);

        Map<Account, MonetaryAmount> accountData = new HashMap<Account, MonetaryAmount>() {
            {

                put(someAccounts.get(0), Monetary.getDefaultAmountFactory()
                        .setCurrency("GBP").setNumber(10000.00).create());

                put(someAccounts.get(1), Monetary.getDefaultAmountFactory()
                        .setCurrency("GBP").setNumber(20000.00).create());
            }
        };

        return accountData;

    }

}
