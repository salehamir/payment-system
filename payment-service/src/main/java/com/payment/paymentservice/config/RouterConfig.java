package com.payment.paymentservice.config;

import com.payment.paymentservice.Handler.PaymentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {


    private static final String BASE_PATH = "/paymentService/v1";

    @Bean
    public RouterFunction<ServerResponse> routes(PaymentHandler paymentHandler) {
        RouterFunctions.Builder routeBuilder = RouterFunctions.route();
        routeBuilder.route(RequestPredicates.GET("/transfer"), paymentHandler::getTransferByQuery);
        routeBuilder.POST(BASE_PATH + "/transfer",
                RequestPredicates.accept(MediaType.APPLICATION_JSON), paymentHandler::transfer);
        return routeBuilder.build();
    }
}
