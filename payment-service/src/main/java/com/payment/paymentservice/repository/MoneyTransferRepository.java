package com.payment.paymentservice.repository;

import com.payment.paymentservice.model.MoneyTransfer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

@Repository
public interface MoneyTransferRepository extends ReactiveSortingRepository<MoneyTransfer, String> {

    Flux<MoneyTransfer> findAllBySourceCardNumberInAndTransactionTimeIsBetweenOrderByTransactionTimeDesc
            (List<String> cardNumbers, Date from,Date to, final Pageable page);
}
