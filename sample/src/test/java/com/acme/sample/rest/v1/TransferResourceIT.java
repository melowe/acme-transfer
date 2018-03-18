package com.acme.sample.rest.v1;

import com.acme.sample.ActualSampleService;
import com.acme.sample.MemoryDatastore;
import com.acme.sample.SampleService;
import com.acme.sample.rest.Main;
import com.acme.sample.rest.SampleApplication;
import com.acme.server.Server;
import com.acme.server.ServerFactory;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransferResourceIT {

    public TransferResourceIT() {
    }

    private static final URI SERVICE_URI = Main.SERVER_URI;

    private static Server server;

    @BeforeClass
    public static void setUp() throws Exception {
        
        SampleService sampleService = new ActualSampleService(new MemoryDatastore(Main.buildAccountData()));
        SampleApplication application = new SampleApplication(sampleService);

        server = ServerFactory.create().createServer(SERVICE_URI, application);

        server.start();

    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void transferValidTranferJson() {

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
                .target(SERVICE_URI)
                .path("/v1/transfers/transfer")
                .request(MediaType.APPLICATION_JSON)
                .buildPost(Entity.entity(transferObject.toString(), MediaType.APPLICATION_JSON))
                .invoke();

        assertThat(reponse).isNotNull();
        assertThat(reponse.getStatus()).isEqualTo(201);

    }
    
    @Test
    public void transferUnknwonToAccountJson() {

        Client client = ClientBuilder.newClient();

        JsonObject transferObject = Json.createObjectBuilder()
                .add("from",
                        Json.createObjectBuilder()
                                .add("number", "1342672"))
                .add("to",
                        Json.createObjectBuilder()
                                .add("number", "999999"))
                .add("amount",
                        Json.createObjectBuilder()
                                .add("amount", 100.98)
                                .add("currency", "GBP")
                ).build();

        javax.ws.rs.core.Response response = client
                .target(SERVICE_URI)
                .path("/v1/transfers/transfer")
                .request(MediaType.APPLICATION_JSON)
                .buildPost(Entity.entity(transferObject.toString(), MediaType.APPLICATION_JSON))
                .invoke();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
        assertThat(response.readEntity(String.class)).contains("999999");

    }
    @Test
    public void transferInvalidCurrency() {

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
                                .add("amount", 10.98)
                                .add("currency", "TMM")
                ).build();

        javax.ws.rs.core.Response response = client
                .target(SERVICE_URI)
                .path("/v1/transfers/transfer")
                .request(MediaType.APPLICATION_JSON)
                .buildPost(Entity.entity(transferObject.toString(), MediaType.APPLICATION_JSON))
                .invoke();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        
        
        assertThat(response.readEntity(String.class)).contains("TMM");

    }
}
