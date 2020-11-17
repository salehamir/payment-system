package com.payment.paymentservice.service;

import com.payment.paymentservice.dto.TransferQuery;
import com.payment.paymentservice.dto.TransferRequest;
import com.payment.paymentservice.kafka.sender.PaymentNotificationSender;
import com.payment.paymentservice.model.MoneyTransfer;
import com.payment.paymentservice.repository.MoneyTransferRepository;
import com.payment.paymentservice.thirdparty.PaymentProvider;
import com.payment.paymentservice.thirdparty.PaymentProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentProviderFactory providerFactory;
    private final MoneyTransferRepository repository;
    private final PaymentNotificationSender notificationSender;

    public Mono<MoneyTransfer> transfer(TransferRequest request){
        PaymentProvider provider = providerFactory.getProvider(request.getSourceCardNumber());
        return provider.transfer(request).map(moneyTransfer -> {
            moneyTransfer.setId(UUID.randomUUID().toString());
            return moneyTransfer;
        }).flatMap(repository::save).map(transfer -> {
            notificationSender.sendPaymentNotification(transfer);
            return transfer;
        });
    }
    public Flux<MoneyTransfer> findTransferByQuery(TransferQuery query){
        return repository.findAllBySourceCardNumberInAndTransactionTimeIsBetweenOrderByTransactionTimeDesc
                (query.getSourceCardNumber(),query.getFrom(),query.getTo(),
                        PageRequest.of(query.getPage(), query.getSize()));
    }



}
