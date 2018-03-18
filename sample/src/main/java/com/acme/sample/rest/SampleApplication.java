
package com.acme.sample.rest;

import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/sample")
public class SampleApplication extends Application {

    @Override
    public Map<String, Object> getProperties() {
        return super.getProperties(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Object> getSingletons() {
        return super.getSingletons(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Class<?>> getClasses() {
        return super.getClasses(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
