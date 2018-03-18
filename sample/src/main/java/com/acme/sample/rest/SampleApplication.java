
package com.acme.sample.rest;

import com.acme.sample.SampleService;
import com.acme.sample.rest.v1.TransferResource;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class SampleApplication extends Application {

    private final SampleService sampleService;

    public SampleApplication(SampleService sampleService) {
        this.sampleService = Objects.requireNonNull(sampleService);
    }
    
    @Override
    public Set<Object> getSingletons() {
        
        TransferResource transferResource  = new TransferResource(sampleService);
        
        TransferExceptionMapper transferExceptionMapper = new TransferExceptionMapper();
        
        DefaultExceptionMapper defaultExceptionMapper = new DefaultExceptionMapper();

        return Stream.of(transferResource,transferExceptionMapper,defaultExceptionMapper)
                .collect(Collectors.toSet());
    }    
    
}
