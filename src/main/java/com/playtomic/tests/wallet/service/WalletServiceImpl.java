package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void topUpWallet(@NotNull Long id,@NotNull  BigDecimal amount) {
        Wallet wallet = getWallet(id).get();
        wallet.updateBalance(amount);
        this.walletRepository.save(wallet);
    }

    @Override
    public BigDecimal getBalance(@NotNull  Long id) {
        Wallet wallet = getWallet(id).get();
        return wallet.getBalance();
    }

    private Optional<Wallet> getWallet(Long id) {
        Optional<Wallet> wallet =this.walletRepository.findById(id);
        if(!wallet.isPresent()){
            throw new WalletException("Wallet nof found",1,"Wallet error");
        }
        return wallet;
    }
}
