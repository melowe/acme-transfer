
package com.acme.sample.rest;

import com.acme.factory.Server;
import com.acme.factory.ServerFactory;
import com.acme.sample.SampleService;
import java.net.InetAddress;
import java.net.URI;
import java.util.logging.LogManager;
import javax.ws.rs.core.UriBuilder;

public class Main {
    
    public static void main(String... args) throws Exception {
    
        System.setProperty("java.util.logging.config.file", Main.class.getResource("/logging.properties").getFile());

        LogManager.getLogManager().readConfiguration();
        
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
