
package com.acme.server.jersey;

import com.acme.server.Server;
import com.acme.server.ServerFactory;
import java.net.URI;
import javax.ws.rs.core.Application;


public class JerseyServerFactory implements ServerFactory {

    @Override
    public Server createServer(URI uri, Application application) {
        return new JerseyServer(uri,application);
    }


    
}
