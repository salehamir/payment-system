package com.payment.user.repository;

import com.payment.user.model.Card;
import com.payment.user.model.UserProfileRef;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CardRepository extends ReactiveMongoRepository<Card,String> {
    Flux<Card> findByUser(UserProfileRef user);
    Mono<Card> findByIdAndAndUser(String cardNumber,UserProfileRef user);

}
