package com.acme.sample;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ActualSampleServiceTest {

    private ActualSampleService service;

    private Datastore datastore;

    public ActualSampleServiceTest() {
    }

    @Before
    public void setUp() {
        datastore = mock(Datastore.class);
        service = new ActualSampleService(datastore);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(datastore);
    }

    @Test
    public void transfer() {
        
        LocalDateTime startTestTime = LocalDateTime.now();
        
        List<Transfer> results = new ArrayList<>();
        Account mockedAccount = mock(Account.class);
        when(mockedAccount.getCurrency()).thenReturn(Monetary.getCurrency("TMM"));

        when(datastore.findAccountByNumber("ACC1234")).thenReturn(mockedAccount);
        when(datastore.findAccountByNumber("ACC4321")).thenReturn(mockedAccount);
        
        doAnswer(i -> results.add(i.getArgument(0)))
                .when(datastore).storeTransaction(any(Transfer.class));

        service.transfer("ACC1234", "ACC4321", new BigDecimal("109.23"), "TMM");

        assertThat(results).hasSize(1);
        
        Transfer transfer = results.get(0);
        
        assertThat(transfer).isNotNull();
        MonetaryAmountFactory monetaryAmountFactory = Monetary.getDefaultAmountFactory();
        
        MonetaryAmount expectedAmount = monetaryAmountFactory
                                                .setCurrency("TMM")
                                                 .setNumber(109.23d).create();
        
        assertThat(transfer.getAmount()).isEqualTo(expectedAmount);
        assertThat(transfer.getFrom()).isSameAs(mockedAccount);
        assertThat(transfer.getTo()).isSameAs(mockedAccount);
        
        assertThat(transfer.getCreated())
                .isNotNull()
                .isBetween(startTestTime, LocalDateTime.now());
  
        verify(datastore).findAccountByNumber("ACC1234");
        verify(datastore).findAccountByNumber("ACC4321");
      
        verify(datastore).storeTransaction(any(Transfer.class));
        
        
    }

}
