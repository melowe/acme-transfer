
package com.acme.sample.rest;

import com.acme.sample.InvalidTransferException;
import com.acme.sample.NoAccountFoundException;
import com.acme.sample.TransferException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransferExceptionMapper implements ExceptionMapper<TransferException>{

    private final  Map<Class<? extends TransferException>,Response.Status> lookup = new HashMap<Class<? extends TransferException>,Response.Status>() {{
        put(NoAccountFoundException.class,Response.Status.NOT_FOUND);
        put(InvalidTransferException.class,Response.Status.BAD_REQUEST);
    }};
    
    @Override
    public Response toResponse(TransferException exception) {

       Response.Status status = lookup.getOrDefault(exception.getClass(), 
                                                        Response.Status.INTERNAL_SERVER_ERROR);
        
        return Response.status(status)
                .entity(exception.getMessage())
                .build();
    }
    
}
