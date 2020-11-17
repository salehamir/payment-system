package com.payment.paymentservice.thirdparty;

import com.payment.paymentservice.thirdparty.impl.PaymentProviderOne;
import com.payment.paymentservice.thirdparty.impl.PaymentProviderTwo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PaymentProviderFactory {

    private final PaymentProviderOne providerOne;
    private final PaymentProviderTwo providerTwo;

    public PaymentProvider getProvider(String cardNumber){
        if(cardNumber.startsWith("6037"))
            return providerOne;
        return providerTwo;
    }
}
