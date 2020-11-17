package com.payment.notification.config;

import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Configuration
@Log
public class ApplicationConfig {
    @Value("${kafka.bootstrapServers}")
    private  String bootstrapServers;
    @Value("${kafka.sms.topic}")
    private String smsTopic;
    @Bean
    public ReceiverOptions<String, String> kafkaOptions(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "notification-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return ReceiverOptions.create(props);
    }

    @Bean
    @Qualifier("smsReceiver")
    public KafkaReceiver<String,String> smsReceiver(ReceiverOptions<String,String> kafkaOptions){
        ReceiverOptions<String, String> options = kafkaOptions
                .subscription(Collections.singleton(smsTopic))
                .addAssignListener(partitions -> log.log(Level.FINE,"onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.log(Level.FINE,"onPartitionsRevoked {}", partitions));
        return KafkaReceiver.create(options);
    }
}
