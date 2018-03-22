package com.acme.sample;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.money.MonetaryAmount;

public class MemoryDatastore implements Datastore {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryDatastore.class);
    
    //TODO: Try lock with time out and review data structure. More defensive use 
    private final  Lock lock = new ReentrantLock();
    
    private final Map<Account, MonetaryAmount> balances;

    public MemoryDatastore(Map<Account, MonetaryAmount> balances) {
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

        Account toAccount = transfer.getTo();

        try {
           lock.lock();
            MonetaryAmount fromAccountAmount = balances.get(fromAccount);
            
            if (fromAccountAmount.isLessThan(amount)) {
                throw new InSuffientFundsException(String.format("%s does not have suffient funds for transfer", fromAccount.getNumber()));
            }
            
            balances.replace(fromAccount, fromAccountAmount,
                    fromAccountAmount.subtract(amount));
            
            MonetaryAmount toAccountAmount = balances.get(toAccount);
            
            balances.replace(toAccount, toAccountAmount,
                    toAccountAmount.add(amount));
        } finally {
            lock.unlock();
        }


        LOGGER.log(Level.INFO, "Transfer {0} from {1} to {2} complete.",
                new String[]{amount.toString(), fromAccount.getNumber(), toAccount.getNumber()});

    }

    @Override
    public MonetaryAmount findBalance(Account account) {
        return balances.get(account);

    }

}
