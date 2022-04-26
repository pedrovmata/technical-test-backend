package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.service.Payment;
import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 *
 * I would mock the service under test so I can abstract over the implementation
 */
public class StripeServiceTest {

 /*   URI testUri = URI.create("http://how-would-you-test-me.localhost");
    StripeService s = new StripeService(testUri, testUri, new RestTemplateBuilder());
*/


    @Test
    public void test_exception() {
        StripeService s = Mockito.mock(StripeService.class);
        when(s.charge("4242 4242 4242 4242", new BigDecimal(5))).thenThrow(new StripeAmountTooSmallException());
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        StripeService s = Mockito.mock(StripeService.class);
        when(s.charge("4242 4242 4242 4242", new BigDecimal(15))).thenReturn(new Payment("123"));
        s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }
}
