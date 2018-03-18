
package com.acme.sample;

import javax.money.MonetaryAmount;


public interface Datastore {
    
    void executeTransfer(Transfer transaction);
    
    Account findAccountByNumber(String accountNumber);
    
    MonetaryAmount findBalance(Account account);
    
}
