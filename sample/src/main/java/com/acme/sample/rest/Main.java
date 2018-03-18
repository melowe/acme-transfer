
package com.acme.sample.rest;

import com.acme.factory.Server;
import com.acme.factory.ServerFactory;
import java.net.InetAddress;
import java.net.URI;
import java.util.ServiceLoader;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;


public class Main {
    
    public static void main(String... args) throws Exception {
    
               //System.setProperty("java.util.logging.config.file", JerseyServer.class.getResource("/logging.properties").getFile());

       // LogManager.getLogManager().readConfiguration();
        
        final String address = InetAddress.getLocalHost().getCanonicalHostName();

        final URI uri = UriBuilder.fromUri("http://" + address + "/").port(8080).build();

        
        SampleApplication application = new SampleApplication();
        Server server = ServerFactory.create().createServer(uri, application);

        server.start();

        System.in.read();

        server.stop();

        System.exit(0);
        
        
        
        
        
    }
    
    
}
