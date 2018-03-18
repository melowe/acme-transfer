
package com.acme.sample;

import java.math.BigDecimal;

public interface SampleService {
    
    void transfer(String from,String to,BigDecimal amount,String currency);
 
    static SampleService create() {
        return new ActualSampleService(MemoryDatastore.INSTANCE);
    }

}
