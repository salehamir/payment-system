package com.payment.user.service;

import com.payment.user.model.UserProfile;
import com.payment.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;

    public Mono<UserProfile> getByUsername(String username) {
        return repository.findById(username);
    }

    public Mono<UserProfile> getByMobile(String mobile) {
        return repository.findByMobile(mobile);
    }

    public Mono<UserProfile> register(UserProfile userProfile) {

        return Mono.just(userProfile)
                .map(seqValue -> {
                    return userProfile;
                }).flatMap(repository::insert);
    }



}
