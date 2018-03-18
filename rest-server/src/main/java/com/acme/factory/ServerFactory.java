
package com.acme.factory;

import java.net.URI;
import java.util.ServiceLoader;
import javax.ws.rs.core.Application;


public interface ServerFactory {
    
    Server createServer(URI uri,Application application);

    static ServerFactory create() {
        ServiceLoader<ServerFactory> loader = ServiceLoader.load(ServerFactory.class);
        return loader.iterator().next();
    }
    
    
}
