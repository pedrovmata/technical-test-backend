package com.playtomic.tests.wallet.sut;

import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class WalletServiceImplTest {
    URI testUri = URI.create("http://localhost:9999");

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
    void topUpWallet_incorrectAmount_throwWalletException() {
        assertThrows(WalletException.class, () -> sut.topUpWallet(1, new BigDecimal(-5)));
    }

}