
package com.acme.sample.rest;

import com.acme.sample.SampleService;
import com.acme.server.Server;
import com.acme.server.ServerFactory;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.util.logging.LogManager;
import javax.ws.rs.core.UriBuilder;

public class Main {
    
    public static void main(String... args) throws Exception {
    
        try (InputStream loggingConfig = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(loggingConfig);
        }
        final String address = InetAddress.getLocalHost().getCanonicalHostName();

        final URI uri = UriBuilder.fromUri("http://0.0.0.0/").port(8080).build();

        SampleService sampleService = SampleService.create();
        SampleApplication application = new SampleApplication(sampleService);
        Server server = ServerFactory.create().createServer(uri, application);

        server.start();

        System.in.read();

        server.stop();

        System.exit(0); 
    }
    
    
}
