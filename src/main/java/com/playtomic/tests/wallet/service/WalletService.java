package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;

public interface WalletService {

    void topUpWallet(Long id, BigDecimal amount);

    BigDecimal getBalance(Long id);
}
