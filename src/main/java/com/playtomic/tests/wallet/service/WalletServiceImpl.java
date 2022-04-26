package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.cache.CacheStore;
import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService{

    public static final String CREDIT_CARD_NUMBER = "4111111111111111";
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private StripeService stripeService;

    @Autowired
    CacheStore<Wallet> walletCache;

    @Override
    @Transactional
    public void topUpWallet(long id,@NotNull  BigDecimal amount) {
        Wallet wallet = getWallet(id);
        walletCache.add(String.valueOf(id), wallet);
        wallet.updateBalance(amount);
        Payment payment = Optional.of(this.stripeService.charge(CREDIT_CARD_NUMBER, amount)).orElseThrow(() -> new StripeServiceException());
        this.walletRepository.save(wallet);
        //uncomment next line to test concurrency error
        //Thread.sleep(30000);
        walletCache.delete(String.valueOf(id));
    }

    @Override
    public BigDecimal getBalance(long id) {
        Wallet wallet = getWallet(id);
        return wallet.getBalance();
    }

    private Wallet getWallet(Long id) {
        Wallet wallet = this.walletRepository.findById(id).orElseThrow(() -> new WalletException("Wallet nof found",1,"Wallet error"));
        return wallet;
    }
}
