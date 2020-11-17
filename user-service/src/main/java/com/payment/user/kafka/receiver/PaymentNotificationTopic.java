package com.payment.user.kafka.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.user.service.NotificationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

@Component
@Scope("singleton")
@Log
public class PaymentNotificationTopic {

    private final Flux<ReceiverRecord<String, String>> paymentNotificationReceiver;
    private final NotificationService service;
    private final SimpleDateFormat dateFormat;
    private final ObjectMapper mapper;

    public PaymentNotificationTopic(@Qualifier("paymentNotificationReceiver") Flux<ReceiverRecord<String, String>> paymentNotificationReceiver, NotificationService service) {
        this.paymentNotificationReceiver = paymentNotificationReceiver;
        this.service = service;
        mapper = new ObjectMapper();
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
    }
    @EventListener(ApplicationStartedEvent.class)
    public void listenTopic() {
        paymentNotificationReceiver.flatMap(record -> {
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
        }).subscribe();

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
