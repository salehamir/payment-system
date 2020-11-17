package com.payment.user.config;

import com.payment.user.handler.CardHandler;
import com.payment.user.handler.UserProfileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {


    private static final String BASE_PATH = "/userProfileService/v1";

    @Bean
    public RouterFunction<ServerResponse> routes(CardHandler cardHandler, UserProfileHandler userProfileHandler) {
        RouterFunctions.Builder routeBuilder = RouterFunctions.route();
        routeBuilder.route(RequestPredicates.GET("/getAllCardByUser/{username}"), cardHandler::getAllCardByUser);
        routeBuilder.POST(BASE_PATH + "/addCard",
                RequestPredicates.accept(MediaType.APPLICATION_JSON), cardHandler::addCard);
        routeBuilder.DELETE(BASE_PATH + "/removeCard/{id}", cardHandler::removeCard);
        routeBuilder.POST(BASE_PATH + "/register",
                RequestPredicates.accept(MediaType.APPLICATION_JSON), userProfileHandler::register);
        return routeBuilder.build();
    }
}
