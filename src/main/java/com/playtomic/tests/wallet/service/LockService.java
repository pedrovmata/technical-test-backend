package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Wallet;

public interface LockService {

    void lockWallet(Wallet wallet);

    void unlockWallet(String  id);
}
