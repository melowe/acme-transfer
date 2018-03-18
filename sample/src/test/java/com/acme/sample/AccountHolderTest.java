package com.acme.sample;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;

public class AccountHolderTest {
    
    public AccountHolderTest() {
    }

    
    @Test
    public void accountHolderWithSameIdAreEqual() {
        
        final AccountHolder accountHolder = new AccountHolder("Some one");
                 
        final AccountHolder anotherAccountHolder = new AccountHolder("Some one");
        
        assertThat(accountHolder)
                .isEqualTo(anotherAccountHolder)
                .isNotSameAs(anotherAccountHolder)
                .isSameAs(accountHolder);
        
        assertThat(accountHolder.equals(anotherAccountHolder)).isTrue();
        
        assertThat(accountHolder.getId()).isNotNull();
        
        assertThat(accountHolder.toString()).isNotNull();
        
        assertThat(accountHolder.hashCode())
                .isEqualTo(anotherAccountHolder.hashCode());

        assertThat(accountHolder.equals(accountHolder)).isTrue();
 
    }

    @Test
    public void sameAccountHolderIsNotEqualsToaDifferentType() {
        final AccountHolder accountHolder = new AccountHolder("Some one");
        Object someOtherType = mock(Object.class);
       
        assertThat(accountHolder.equals(someOtherType)).isFalse();
    }

    @Test
    public void sameAccountHolderIsEqual() {
        final AccountHolder accountHolder = new AccountHolder("Some one");
        assertThat(accountHolder.equals(accountHolder)).isTrue();
    }
    
    @Test
    public void nullValueIsnotEqual() {
        final AccountHolder accountHolder = new AccountHolder("Some one");
        assertThat(accountHolder.equals(null)).isFalse();
    }
    
    @Test
    public void accountHolderWithDifferentIdsAreNotEqual() {
        
        AccountHolder accountHolder = new AccountHolder("SomeId");
                 
        AccountHolder anotherAccountHolder = new AccountHolder("SomeOtherId");
        
        assertThat(accountHolder.equals(anotherAccountHolder)).isFalse();
        
        assertThat(accountHolder.hashCode())
                .isNotEqualTo(anotherAccountHolder.hashCode());

    } 
    
    
    
    
    @Test(expected = NullPointerException.class)
    public void dontAllowCreationWithoutId() {
        new AccountHolder(null);
    }
}
