
package com.acme.sample;


public class InSuffientFundsException extends TransferException{
    
    public InSuffientFundsException(String message) {
        super(message);
    }
    
}
