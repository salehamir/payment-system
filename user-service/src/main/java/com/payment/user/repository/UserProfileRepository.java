package com.payment.user.repository;

import com.payment.user.model.UserProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile,String> {
    Mono<UserProfile> findByMobile(String mobile);
}
