
package com.acme.sample.rest;

import com.acme.sample.Account;
import com.acme.sample.ActualSampleService;
import com.acme.sample.MemoryDatastore;
import com.acme.sample.SampleService;
import com.acme.server.Server;
import com.acme.server.ServerFactory;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.LogManager;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.ws.rs.core.UriBuilder;

public class Main {
    
    //TODO: Define from args
    public static final URI SERVER_URI = UriBuilder.fromUri("http://0.0.0.0/").port(8080).build();
    
    public static void main(String... args) throws Exception {
    
        try (InputStream loggingConfig = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(loggingConfig);
        }

        ConcurrentMap<Account,MonetaryAmount> accounts = buildAccountData();
        MemoryDatastore datastore = new MemoryDatastore(accounts);
        
        SampleService sampleService = new ActualSampleService(datastore);
        SampleApplication application = new SampleApplication(sampleService);
       
        Server server = ServerFactory.create().createServer(SERVER_URI, application);

        server.start();

        System.in.read();

        server.stop();

        System.exit(0); 
    }
    
    public static ConcurrentMap<Account,MonetaryAmount> buildAccountData() {
          List<Account> someAccounts =  Arrays.asList( 
                    Account.Builder.create()
                        .number("1342672")
                        .currency("GBP")
                        .accountHolder("HOLDER1")
                        .build(),
                    Account.Builder.create()
                        .number("2882882")
                        .currency("GBP")
                        .accountHolder("HOLDER1")
                        .build());
        
          ConcurrentMap<Account,MonetaryAmount> accountData = new ConcurrentHashMap<Account,MonetaryAmount>(){{
                
              put(someAccounts.get(0),Monetary.getDefaultAmountFactory()
                        .setCurrency("GBP").setNumber(1928.99).create());
                
                put(someAccounts.get(1),Monetary.getDefaultAmountFactory()
                        .setCurrency("GBP").setNumber(19.01).create());
          }};
        
            return accountData;

    }
    
    
}
