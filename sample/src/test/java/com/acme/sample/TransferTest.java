package com.acme.sample;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

public class TransferTest {
    
    @Test
    public void buildValid() {

        MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                .setCurrency("GBP")
                .setNumber(1).create();


        Account from = Account.Builder.create()
                .number("123456")
                .accountHolder("SomeOne")
                .currency("GBP")
                .build();

        Account to = Account.Builder.create()
                .number("987654")
                .accountHolder("SomeOneElse")
                .currency("GBP")
                .build();

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
                .setNumber(1).create();

        Account from = Account.Builder.create()
                .number("123456")
                .accountHolder("SomeOne")
                .currency("GBP")
                .build();

        Account to = Account.Builder.create()
                .number("987654")
                .accountHolder("SomeOneElse")
                .currency("USD")
                .build();
        

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


}
