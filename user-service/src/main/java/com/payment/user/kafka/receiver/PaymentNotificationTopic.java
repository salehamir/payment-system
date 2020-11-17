package com.payment.user.kafka.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.user.service.NotificationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

@Component
@Log
public class PaymentNotificationTopic {

    private final KafkaReceiver<String, String> paymentNotificationReceiver;
    private final NotificationService service;
    private final SimpleDateFormat dateFormat;
    private final ObjectMapper mapper;

    public PaymentNotificationTopic(@Qualifier("paymentNotificationReceiver") KafkaReceiver<String, String> paymentNotificationReceiver, NotificationService service) {
        this.paymentNotificationReceiver = paymentNotificationReceiver;
        this.service = service;
        mapper = new ObjectMapper();
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
    }

    @PostConstruct
    public Flux<Boolean> listenTopic() {
        return paymentNotificationReceiver.receive().flatMap(record -> {
            ReceiverOffset offset = record.receiverOffset();
            log.log(Level.INFO, String.format(
                    "Received message: topic-partition=%s offset=%d timestamp=%s key=%s value=%s",
                    offset.topicPartition(),
                    offset.offset(),
                    dateFormat.format(new Date(record.timestamp())),
                    record.key(),
                    record.value()));
            return convertValue(record.value())
                    .flatMap(service::sendNotification)
                    .map(res -> {
                        if (res) {
                            offset.acknowledge();
                            return res;
                        } else {
                            log.log(Level.INFO, String.format(
                                    "not send payment notification: key=%d value=%s",
                                    record.key(),
                                    record.value()));
                            return res;
                        }
                    });
        });

    }

    private Mono<PaymentNotification> convertValue(String value) {
        try {
            return Mono.just(mapper.readValue(value, new TypeReference<PaymentNotification>() {
            }));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }
}
