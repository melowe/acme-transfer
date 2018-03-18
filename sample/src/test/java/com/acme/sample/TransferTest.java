package com.acme.sample;

import java.util.Locale;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import static org.assertj.core.api.Assertions.*;
import org.javamoney.moneta.Money;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferTest {

    public TransferTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void buildValid() {

        MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                .setCurrency("GBP")
                .setNumber(100.01).create();

        Account from = mock(Account.class);

        Account to = mock(Account.class);
        when(to.getCurrency()).thenReturn(amount.getCurrency());

        Transfer t = Transfer.Builder.create()
                                            .created()
                                            .amount(amount)
                                            .from(from)
                                            .to(to)
                                            .build();

        assertThat(t).isNotNull();

    }

    @Test
    public void cannotTransferWrongCurrencyToAccount() {
        
        MonetaryAmountFactory monetaryAmountFactory = Monetary.getDefaultAmountFactory();

        MonetaryAmount amount = monetaryAmountFactory
                .setCurrency("GBP")
                .setNumber(100.01).create();

        Account from = mock(Account.class);

        Account to = mock(Account.class);
        when(to.getCurrency()).thenReturn(Monetary.getCurrency("USD"));

        try {
            Transfer.Builder.create()
                    .created()
                    .amount(amount)
                    .from(from)
                    .to(to)
                    .build();
            failBecauseExceptionWasNotThrown(InvalidTransferException.class);
        } catch (InvalidTransferException ex) {
            assertThat(ex.getMessage()).isNotBlank();
        }

    }
    
    
    @Test
    public void buildInValidDecimalPlacesAmount() {

        MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                .setCurrency("GBP")
                
                .setNumber(100.00199).create();

       ;
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.getDefault());

        System.out.println(format.format( Money.of(100.1922, "USD")));
       
        Account from = mock(Account.class);

        Account to = mock(Account.class);
        when(to.getCurrency()).thenReturn(amount.getCurrency());

        Transfer t = Transfer.Builder.create()
                                            .created()
                                            .amount(amount)
                                            .from(from)
                                            .to(to)
                                            .build();

        assertThat(t).isNotNull();

    }
}
