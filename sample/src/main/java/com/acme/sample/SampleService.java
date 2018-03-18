
package com.acme.sample;

import java.math.BigDecimal;
import javax.money.MonetaryAmount;

public interface SampleService {
    
    
    
    void transfer(String from,String to,BigDecimal amount,String currency);
    
    MonetaryAmount findBalanceForAccountNumber(String number);
    
    
}
