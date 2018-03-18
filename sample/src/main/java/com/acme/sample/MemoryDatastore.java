
package com.acme.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public enum MemoryDatastore implements Datastore {
    
    INSTANCE;

    private final List<Transfer> movements = new ArrayList<>();
    
    private static final String DEFAULT_ACCOUNT_HOLDER = "HOLDER1";
    
    private static final List<Account> ACCOUNTS = 
            Arrays.asList( 
                    Account.Builder.create()
                        .number("1342672")
                        .currency("GBP")
                        .accountHolder(DEFAULT_ACCOUNT_HOLDER)
                        .build(),
                    Account.Builder.create()
                        .number("2882882")
                        .currency("GBP")
                        .accountHolder(DEFAULT_ACCOUNT_HOLDER)
                        .build());
    
    

    @Override
    public Account findAccountByNumber(String accountNumber) {

        return ACCOUNTS.stream()
                .filter(a -> a.getNumber().equals(accountNumber))
                .findAny()
                .orElseThrow(() -> new NoAccountFoundException(accountNumber));
        
    }

    @Override
    public List<Account> findAccountsForHolder(AccountHolder accountHolder) {
        return ACCOUNTS.stream()
                .filter(a -> Objects.equals(a.getAccountHolder(), accountHolder))
                .collect(Collectors.toList());
    }
    
    @Override
    public void storeTransaction(Transfer transaction) {
        movements.add(transaction);
    }
 
}
