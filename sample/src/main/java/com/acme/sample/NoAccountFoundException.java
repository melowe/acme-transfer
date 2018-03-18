
package com.acme.sample;


public class NoAccountFoundException extends TransferException {

    public NoAccountFoundException(String accountNumber) {
        super(String.format("No account found for number %s", accountNumber));
        
    }
    
}
