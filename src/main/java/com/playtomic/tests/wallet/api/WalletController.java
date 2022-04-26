package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.cache.CacheStore;
import com.playtomic.tests.wallet.exception.WalletException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;
    @Autowired
    private CacheStore<Wallet> cacheWallet;

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping(value = "/wallet/{id}/balance", produces = MediaTypes.HAL_JSON_VALUE)
    public BigDecimal getBalance(@PathVariable("id") final long id){
        return this.walletService.getBalance(id);
    }

    @PutMapping(value = "/wallet/{id}/balance/{amount}", produces = MediaTypes.HAL_JSON_VALUE)
    public HttpStatus topUpWallet(@PathVariable("id") final long id, @PathVariable("amount") final BigDecimal amount) {
        Wallet walletCached = this.cacheWallet.get(String.valueOf(id));
        if(walletCached!=null){
            return HttpStatus.CONFLICT;
        }
        this.walletService.topUpWallet(id,amount);
        return HttpStatus.OK;
    }

}
