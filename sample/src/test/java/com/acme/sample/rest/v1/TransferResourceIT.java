package com.acme.sample.rest.v1;

import com.acme.server.Server;
import com.acme.server.ServerFactory;
import com.acme.sample.SampleService;
import com.acme.sample.rest.SampleApplication;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransferResourceIT {

    public TransferResourceIT() {
    }

    final URI uri = UriBuilder.fromUri("http://127.0.0.1:8080/v1/transfers/transfer").build();

    private Server server;

    @Before
    public void setUp() throws Exception {

        SampleService sampleService = SampleService.create();
        SampleApplication application = new SampleApplication(sampleService);

        server = ServerFactory.create().createServer(uri, application);

        server.start();

    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void transferHappyCase() {

        Client client = ClientBuilder.newClient();

        JsonObject transferObject = Json.createObjectBuilder()
                .add("from",
                        Json.createObjectBuilder()
                                .add("number", "1342672"))
                .add("to",
                        Json.createObjectBuilder()
                                .add("number", "2882882"))
                .add("amount",
                        Json.createObjectBuilder()
                                .add("amount", 100.98)
                                .add("currency", "GBP")
                ).build();

        javax.ws.rs.core.Response reponse = client
                .target(uri)
                .request(MediaType.APPLICATION_JSON)
                .buildPost(Entity.entity(transferObject.toString(), MediaType.APPLICATION_JSON))
                .invoke();

        assertThat(reponse).isNotNull();
        assertThat(reponse.getStatus()).isEqualTo(201);

    }

}
