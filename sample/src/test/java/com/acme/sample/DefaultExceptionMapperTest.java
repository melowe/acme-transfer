
package com.acme.sample;

import com.acme.sample.rest.DefaultExceptionMapper;
import javax.ws.rs.core.Response;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DefaultExceptionMapperTest {
    
    private final DefaultExceptionMapper mapper = new DefaultExceptionMapper();

    @Test
    public void toResponse() {

        Exception exception = new Exception("OUCH That's gotta smart!!");
        Response response  = mapper.toResponse(exception);
        
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getEntity()).isEqualTo("OUCH That's gotta smart!!");
        
    }
    
    
}
