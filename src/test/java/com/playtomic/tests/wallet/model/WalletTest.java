package com.playtomic.tests.wallet.model;

import com.playtomic.tests.wallet.exception.WalletException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class WalletTest {

    @Test
    void updateBalance_amountIsNull_throwNullPointerException() {
        //Arrange
        Wallet wallet = new Wallet();
        //Assert
        assertThrows(NullPointerException.class,() -> wallet.updateBalance(null));
    }


    @Test
    void updateBalance_amountIsNegative_throwWalletException() {
        //Arrange
        Wallet wallet = new Wallet();
        //Assert
        assertThrows(WalletException.class,() -> wallet.updateBalance(BigDecimal.valueOf(-20)));
    }
}