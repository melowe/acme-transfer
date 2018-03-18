package com.acme.sample.rest;

import com.acme.sample.SampleService;
import com.acme.sample.rest.v1.TransferResource;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SampleApplicationTest {
    
    private SampleApplication sampleApplication;
    
    private SampleService sampleService;
    
    public SampleApplicationTest() {
    }
    
    @Before
    public void setUp() {
        sampleService = mock(SampleService.class);
        sampleApplication = new  SampleApplication(sampleService);
    }
    
    @After
    public void tearDown() {
        verifyNoMoreInteractions(sampleService);
    }
    
    @Test
    public void getSingletons() {
      Set<Object> results =   sampleApplication.getSingletons();
      assertThat(results)
              .hasSize(3);
      
      assertThat(results)
              .anyMatch(DefaultExceptionMapper.class::isInstance)
              .anyMatch(TransferExceptionMapper.class::isInstance)
              .anyMatch(TransferResource.class::isInstance);

    }
        
    
}
