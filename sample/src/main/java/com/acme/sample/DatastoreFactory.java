package com.acme.sample;

import java.util.ServiceLoader;

public interface DatastoreFactory {

    Datastore createDatastore();

    static DatastoreFactory create() {
        ServiceLoader<DatastoreFactory> loader = ServiceLoader.load(DatastoreFactory.class);
        if (loader.iterator().hasNext()) {
            return loader.iterator().next();
        }
        return () -> MemoryDatastore.INSTANCE;
    }

}
