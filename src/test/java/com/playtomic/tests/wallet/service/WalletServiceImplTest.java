package com.playtomic.tests.wallet.sut;

import com.playtomic.tests.wallet.cache.CacheStore;
import com.playtomic.tests.wallet.exception.ConflictException;
import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class WalletServiceImplTest {


    @Autowired
    private WalletService sut;


    @Test
    void topUpWallet_walletNotFound_throwWalletException() {
        // Arrange
        Wallet wallet = new Wallet(8, new BigDecimal(55));
        // Assert
        assertThrows(WalletException.class, () -> sut.topUpWallet(8, new BigDecimal(5)));
    }

    @Test
    void topUpWallet_updateBalance_endsFine() throws InterruptedException {
        //Act
        sut.topUpWallet(1, new BigDecimal(20));
        BigDecimal actual = sut.getBalance(1);
        //Assert
        assertTrue(actual.compareTo(new BigDecimal(70)) == 0);
    }

    @Test
    void topUpWallet_incorrectAmount_throwWalletException() {
        assertThrows(WalletException.class, () -> sut.topUpWallet(1, new BigDecimal(-5)));
    }

}