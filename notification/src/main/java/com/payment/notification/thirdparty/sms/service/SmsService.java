package com.payment.notification.thirdparty.sms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.payment.notification.model.Notification;
import com.payment.notification.thirdparty.sms.model.SmsRequest;
import com.payment.notification.thirdparty.sms.model.SmsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class SmsService {

    private final WebClient webClient;
    @Value("${thirdparty.sms.url}")
    private String smsUrl;

    public SmsService() {
        this.webClient = WebClient.builder().build();
    }


    public Mono<Boolean> sendSms(Notification notification) {
        SmsRequest request = new SmsRequest(notification.getMessage(), notification.getMobile());
        return webClient.post()
                .uri(URI.create(smsUrl))
                .body(BodyInserters.fromValue(request))
                .exchangeToMono(clientResponse -> checkError(clientResponse)
                        .flatMap(res -> res.bodyToMono(SmsResponse.class)))
                .map(res -> res.getStatus() != null && res.getStatus().equalsIgnoreCase("ok"));
    }

    private Mono<ClientResponse> checkError(ClientResponse clientResponse) {
        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(JsonNode.class).flatMap(res -> Mono.error(
                    new RuntimeException("rest call error status code : " + clientResponse.statusCode()
                            + " , response : " + res)));
        }
        return Mono.just(clientResponse);
    }


}
