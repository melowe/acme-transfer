package com.acme.sample;

import javax.money.Monetary;
import javax.money.MonetaryException;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AccountTest {

    public AccountTest() {
    }

    @Test
    public void create() {
        Account account = Account.Builder.create()
                .accountHolder("1234")
                .currency("HKD")
                .number("ACCOUNT123")
                .build();

        assertThat(account).isNotNull();
        assertThat(account.getAccountHolder()).isNotNull();
        assertThat(account.getAccountHolder().getId()).isEqualTo("1234");
        assertThat(account.getNumber()).isEqualTo("ACCOUNT123");
        assertThat(account.getCurrency()).isEqualTo(Monetary.getCurrency("HKD"));
    }

    @Test(expected = NullPointerException.class)
    public void createWithMissingCurrency() {
        Account.Builder.create()
                .accountHolder("1234")
                .number("ACCOUNT123")
                .build();
    }

    @Test(expected = MonetaryException.class)
    public void createWithInvalidCurrency() {
        Account.Builder.create()
                .currency("YYY")
                .accountHolder("1234")
                .number("ACCOUNT123")
                .build();

    }

    @Test(expected = NullPointerException.class)
    public void createWithMissingAccountHolder() {
        Account.Builder.create()
                .number("ACCOUNT123")
                .currency("HKD")
                .build();
    }

    @Test
    public void accountWithSameNumberAreEqual() {
        Account account = Account.Builder.create()
                .accountHolder("5432")
                .currency("GBP")
                .number("ACCOUNT123")
                .build();
        
       Account anotherAccount = Account.Builder.create()
                .accountHolder("1234")
                .currency("HKD")
                .number("ACCOUNT123")
                .build();
       
       assertThat(account).isEqualTo(anotherAccount);
       assertThat(account.hashCode()).isEqualTo(anotherAccount.hashCode());   
    }
    
    @Test
    public void sameAccountIsAlsoEqual() {
        
        Account account = Account.Builder.create()
                .accountHolder("5432")
                .currency("GBP")
                .number("ACCOUNT123")
                .build();

       assertThat(account).isEqualTo(account);
       
    }
    
    @Test
    public void accountIsNotEqualToDifferentType() {
        
        Object nonAccount = mock(Object.class);
        
        Account account = Account.Builder.create()
                .accountHolder("5432")
                .currency("GBP")
                .number("ACCOUNT123")
                .build();

       assertThat(account).isNotEqualTo(nonAccount);  
    }
    
    @Test
    public void accountIsNotEqualToNull() {
        
        Account nullAccount = null;
        
        Account account = Account.Builder.create()
                .accountHolder("5432")
                .currency("GBP")
                .number("ACCOUNT123")
                .build();

        assertThat(account).isNotEqualTo(nullAccount);
       
    }
    
    @Test
    public void toStringIsNotNullOrEmpty() {

        Account account = Account.Builder.create()
                .accountHolder("5432")
                .currency("GBP")
                .number("ACCOUNT123")
                .build();

        assertThat(account.toString()).isNotNull().isNotEmpty();
       
    }
    
}
