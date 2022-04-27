package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    public BigDecimal getBalance(@PathVariable("id") final long id) {
        return this.walletService.getBalance(id);
    }

    @PutMapping(value = "/wallet/{id}/balance/{amount}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> topUpWallet(@PathVariable("id") final long id, @PathVariable("amount") final BigDecimal amount){
        this.walletService.topUpWallet(id, amount);
        return ResponseEntity.ok("Top up completed successfully");
    }


}
