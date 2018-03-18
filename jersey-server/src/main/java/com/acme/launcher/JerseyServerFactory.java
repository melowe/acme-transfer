
package com.acme.launcher;

import com.acme.factory.Server;
import com.acme.factory.ServerFactory;
import java.net.URI;
import javax.ws.rs.core.Application;


public class JerseyServerFactory implements ServerFactory {

    @Override
    public Server createServer(URI uri, Application application) {
        return new JerseyServer(uri,application);
    }


    
}
