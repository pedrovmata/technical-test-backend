package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    public static final String CREDIT_CARD_NUMBER = "4111111111111111";
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private LockService lockService;

    @Override
    @Transactional
    public void topUpWallet(long id, @NotNull BigDecimal amount) {
        Wallet wallet = getWallet(id);
        lockService.lockWallet(wallet);
        try {
            update(amount, wallet);
        } catch (Exception e) {
            throw new WalletException(e.getMessage(), 1, "Error");
        } finally {
            lockService.unlockWallet(String.valueOf(id));
        }

    }

    private void update(BigDecimal amount, Wallet wallet)  {
        wallet.updateBalance(amount);
        this.stripeService.charge(CREDIT_CARD_NUMBER, amount);
        this.walletRepository.save(wallet);
        //uncomment next line to test concurrency error
        //Thread.sleep(30000);
    }


    @Override
    public BigDecimal getBalance(long id) {
        return getWallet(id).getBalance();
    }

    private Wallet getWallet(Long id) {
        return this.walletRepository.findById(id).orElseThrow(() -> new WalletException("Wallet nof found", 1, "Wallet error"));
    }
}
