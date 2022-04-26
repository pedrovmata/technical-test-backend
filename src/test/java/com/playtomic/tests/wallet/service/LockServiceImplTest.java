package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.cache.CacheStore;
import com.playtomic.tests.wallet.exception.ConflictException;
import com.playtomic.tests.wallet.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class LockServiceImplTest {

    @Mock
    CacheStore<Wallet> cache;

    @InjectMocks
    LockServiceImpl sut;

    @Test
    void lockWallet_walletCached_throwConflictException() {
        //Arrange
        Wallet wa = new  Wallet(1,new BigDecimal(20));
        //Act
        when(cache.get("1")).thenReturn(new Wallet(1,new BigDecimal(20)));
        //Asserts
        assertThrows(ConflictException.class,() -> sut.lockWallet(wa));
    }

    @Test
    void lockWallet_walletNotCached_endsOk() {
        //Arrange
        Wallet wa = new  Wallet(1,new BigDecimal(20));
        //Act
        when(cache.get("1")).thenReturn(null);
        //Asserts
        sut.lockWallet(wa);
    }


}