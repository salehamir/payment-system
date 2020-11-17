package com.payment.user.handler;

import com.payment.user.enumeration.ResponseCode;
import com.payment.user.model.ResponseModel;
import com.payment.user.model.UserProfile;
import com.payment.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserProfileHandler {

    private final UserProfileService service;

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(UserProfile.class)
                .flatMap(service::register)
                .flatMap(res -> ServerResponse
                        .ok()
                        .body(BodyInserters
                                .fromValue(new ResponseModel<UserProfile>
                                        (ResponseCode.OK, "", res))));
    }


}
