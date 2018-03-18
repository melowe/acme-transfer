
package com.acme.sample;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.money.MonetaryAmount;


public class MemoryDatastore implements Datastore {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryDatastore.class);
    
    private final ConcurrentMap<Account,MonetaryAmount> balances;
    
    public MemoryDatastore(ConcurrentMap<Account,MonetaryAmount> balances) {
        this.balances = Objects.requireNonNull(balances);
    }
    
    @Override
    public Account findAccountByNumber(String accountNumber) {
        return balances.keySet().stream()
                .filter(a -> a.getNumber().equals(accountNumber))
                .findAny()
                .orElseThrow(() -> new NoAccountFoundException(accountNumber));
    }

    @Override
    public void executeTransfer(Transfer transfer) { 
        
        MonetaryAmount amount = transfer.getAmount();
        
        Account fromAccount = transfer.getFrom();
        
        if(balances.get(fromAccount).isLessThan(amount)) {
            throw new InSuffientFundsException(String.format("%s does not have suffient funds for transfer",fromAccount.getNumber()));
        }
        
        
        balances.replace(fromAccount,
                balances.get(fromAccount).subtract(amount));
        
        Account toAccount = transfer.getTo();
        
        balances.replace(toAccount, 
                balances.get(toAccount).add(amount));
        
        LOGGER.log(Level.INFO,"Transfer {0} from {1} to {2} complete.",
                            new String[] {amount.toString(),fromAccount.getNumber(),toAccount.getNumber()});

    }

    @Override
    public MonetaryAmount findBalance(Account account) {
        return balances.get(account);
        
    }
    
    
    
 
}
