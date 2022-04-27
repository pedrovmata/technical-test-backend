package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.cache.CacheStore;
import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LockServiceImpl implements LockService {

    @Autowired
    CacheStore<Wallet> walletCache;

    @Override
    public void lockWallet(Wallet wallet) {
        Wallet walletCached = this.walletCache.get(String.valueOf(wallet.getId()));
        if(walletCached!=null){
            throw new WalletException("Error, another transaction is in process please try later",1,"Error");
        }
        walletCache.add(String.valueOf(wallet.getId()), wallet);
        log.info("Record stored in cache with key " + String.valueOf(wallet.getId()));
    }

    @Override
    public void unlockWallet(String  id) {
        walletCache.delete(id);
        log.info("Record delete from cache with key " + id);
    }
}
