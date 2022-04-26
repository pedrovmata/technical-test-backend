package com.playtomic.tests.wallet.cache;

import com.playtomic.tests.wallet.model.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Wallet> walletCache() {
        return new CacheStore<Wallet>(240, TimeUnit.SECONDS);
    }

}
