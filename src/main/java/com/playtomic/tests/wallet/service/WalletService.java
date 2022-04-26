package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;

public interface WalletService {

    void topUpWallet(long id, BigDecimal amount);

    BigDecimal getBalance(long id);
}
