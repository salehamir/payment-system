package com.payment.user.service;

import com.payment.user.model.UserProfile;
import com.payment.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserProfile> getByUsername(String username) {
        return repository.findById(username);
    }

    public Mono<UserProfile> getByMobile(String mobile) {
        return repository.findByMobile(mobile);
    }

    public Mono<UserProfile> register(UserProfile userProfile) {

        return Mono.just(userProfile)
                .map(seqValue -> {
                    userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
                    return userProfile;
                }).flatMap(repository::insert);
    }

    public boolean isMatches(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }


}
