package com.acme.sample.rest.v1;

import com.acme.sample.SampleService;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//TODO:Design. Should transfer between accounts be a account resource call?
@Path("v1/transfers")
public class TransferResource {
    
    private final SampleService service;

    public TransferResource(SampleService service) {
        this.service = service;
    }
    
    @POST
    @Path("transfer")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response transfer(final com.acme.api.v1.Transfer transfer) {
    
        final String from = transfer.getFrom().getNumber();
        final String to = transfer.getTo().getNumber();
        final BigDecimal amount = transfer.getAmount().getAmount();
        final String currency = transfer.getAmount().getCurrency();

        service.transfer(from, to, amount, currency);
        return Response.status(Response.Status.CREATED).build();
    }
    
    
}
