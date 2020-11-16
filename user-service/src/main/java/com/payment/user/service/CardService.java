package com.payment.user.service;

import com.payment.user.model.Card;
import com.payment.user.model.UserProfileRef;
import com.payment.user.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository repository;


    public Flux<Card> getAllByUser(String username) {
        return Mono.just(new UserProfileRef(username))
                .flatMapMany(repository::findByUser);
    }

    public Mono<Card> getByCardNumber(String cardNumber) {
        return repository.findById(cardNumber);
    }


    private Mono<Card> getByIdAndUser(String cardNumber, String username) {
        return Mono.just(new UserProfileRef(username))
                .flatMap(user -> repository.findByIdAndAndUser(cardNumber, user));
    }

    public Mono<Card> addCard(Card card) {
        return repository.insert(card);
    }

    public Mono<Void> removeCard(String cardNumber) {
        return repository.deleteById(cardNumber);
    }

}
