package com.payment.user.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

@Component
@Log
public class NotificationSender {

    private final KafkaSender<String,String> kafkaSender;
    private final ObjectMapper mapper;
    private final SimpleDateFormat dateFormat;
    @Value("${kafka.sms.topic}")
    private String smsTopic;



    @Autowired
    public NotificationSender(KafkaSender<String, String> kafkaSender) {
        this.kafkaSender = kafkaSender;
        this.mapper = new ObjectMapper();
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
    }

    public void sendNotification(Notification notification) {
        Mono.just(notification)
                .flatMap(res -> {
                    try {
                        return Mono.just(new ProducerRecord<>(smsTopic, res.getId(),
                                mapper.writeValueAsString(res)));
                    } catch (JsonProcessingException e) {
                        log.info("error for json processing ");
                        return Mono.empty();
                    }
                })
                .map(record->SenderRecord.create(record, record.key()))
                .flatMapMany(record->kafkaSender.<String>send(Mono.just(record)))
                .doOnError(e -> log.log(Level.INFO,"Send failed", e))
                .subscribe(r -> {
                    RecordMetadata metadata = r.recordMetadata();
                    log.info(String.format("Message %s sent successfully, topic-partition=%s-%d offset=%d timestamp=%s\n",
                            r.correlationMetadata(),
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            dateFormat.format(new Date(metadata.timestamp()))));
                });
    }
}
