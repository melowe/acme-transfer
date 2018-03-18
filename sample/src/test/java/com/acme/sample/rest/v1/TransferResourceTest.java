package com.acme.sample.rest.v1;

import com.acme.api.v1.ObjectFactory;
import com.acme.sample.SampleService;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


public class TransferResourceTest {
    
    private TransferResource transferResource;
    
    private SampleService service;
    
    public TransferResourceTest() {

    }
    
    @Before
    public void setUp() {
         service = mock(SampleService.class);
         transferResource = new TransferResource(service);
    }
    
    @After
    public void tearDown() {
        verifyNoMoreInteractions(service);
    }
    
    @Test
    public void transfer() {
    
        ObjectFactory objectFactory = new ObjectFactory();
        
        com.acme.api.v1.Transfer transfer = objectFactory.createTransfer();
        transfer.setFrom(objectFactory.createAccount());
        transfer.setTo(objectFactory.createAccount());
        transfer.setAmount(objectFactory.createMonetaryAmount());
        
        transfer.getFrom().setNumber("ACCOUNT123");
        transfer.getTo().setNumber("ACCOUNT321");
        transfer.getAmount().setAmount(new BigDecimal("99.12"));
        transfer.getAmount().setCurrency("TMM");

        transferResource.transfer(transfer);
        
        verify(service).transfer("ACCOUNT123", "ACCOUNT321",new BigDecimal("99.12"), "TMM");
        
        
        
        
    }
    
}
