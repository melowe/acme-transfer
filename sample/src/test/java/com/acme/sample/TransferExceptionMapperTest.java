
package com.acme.sample;

import com.acme.sample.rest.TransferExceptionMapper;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class TransferExceptionMapperTest {


    @Test
    public void noAccountFoundExceptiontoResponse() {
        TransferExceptionMapper mapper = new TransferExceptionMapper();
        
        javax.ws.rs.core.Response response =  mapper.toResponse(new NoAccountFoundException("99999"));
        
        assertThat(response).isNotNull();
        assertThat(response.getStatus())
                .isEqualTo(javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode());
        assertThat(response.getEntity()).asString().contains("99999");
        
    }
    
    
    @Test
    public void inValidTransferExceptiontoResponse() {
        TransferExceptionMapper mapper = new TransferExceptionMapper();
        
        javax.ws.rs.core.Response response =  mapper.toResponse(new InvalidTransferException("99999"));
        
        assertThat(response).isNotNull();
        assertThat(response.getStatus())
                .isEqualTo(javax.ws.rs.core.Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(response.getEntity()).asString().contains("99999");
        
    }
}
