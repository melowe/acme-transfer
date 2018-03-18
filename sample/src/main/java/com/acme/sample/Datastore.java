
package com.acme.sample;

import java.util.List;

public interface Datastore {
    
    void storeTransaction(Transfer transaction);
    
    Account findAccountByNumber(String accountNumber);
    
    List<Account> findAccountsForHolder(AccountHolder accountHolder);

    
}
