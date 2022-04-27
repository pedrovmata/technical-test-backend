package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.service.Payment;
import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 *
 */
@ExtendWith(MockitoExtension.class)
public class StripeServiceTest {

    URI testUri = URI.create("http://how-would-you-test-me.localhost");
    StripeService s;

    @Mock
    RestTemplateBuilder restTemplateBuilder;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        Mockito.when(restTemplateBuilder.errorHandler(Mockito.any())).thenReturn(restTemplateBuilder);
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
        s = new StripeService(testUri, testUri, restTemplateBuilder);
    }

    @Test
    public void test_exception() {
        //When
        Mockito.when(restTemplate.postForObject(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(StripeAmountTooSmallException.class);
        //Asserts
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        // When
        Mockito.when(restTemplate.postForObject(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new Payment(""));
        s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }
}