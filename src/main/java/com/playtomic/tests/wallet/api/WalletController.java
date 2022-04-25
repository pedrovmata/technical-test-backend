package com.playtomic.tests.wallet.api;

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
import java.util.Optional;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping(value = "/wallet/{id}/balance", produces = MediaTypes.HAL_JSON_VALUE)
    public BigDecimal getBalance(@PathVariable("id") final long id){
        return this.walletService.getBalance(id);
    }

    @PutMapping(value = "/wallet/{id}/balance/{amount}", produces = MediaTypes.HAL_JSON_VALUE)
    public HttpStatus topUpWallet(@PathVariable("id") final long id, @PathVariable("amount") final BigDecimal amount){
        this.walletService.topUpWallet(id,amount);
        return HttpStatus.OK;
    }

}
