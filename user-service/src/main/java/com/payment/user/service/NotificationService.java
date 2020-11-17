package com.payment.user.service;

import com.payment.user.kafka.receiver.PaymentNotification;
import com.payment.user.kafka.sender.Notification;
import com.payment.user.kafka.sender.NotificationSender;
import com.payment.user.model.Card;
import com.payment.user.model.UserProfileRef;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final CardService cardService;
    private final UserProfileService userProfileService;
    private final NotificationSender sender;

    public Mono<Boolean> sendNotification(PaymentNotification paymentNotification) {
        return Mono.just(paymentNotification)
                .flatMap(res -> {
                    return cardService.getByCardNumber(res.getSourceCardNumber())
                            .map(Card::getUser)
                            .map(UserProfileRef::getUsername)
                            .flatMap(userProfileService::getByUsername)
                            .map(user -> {
                                Notification notification = new Notification();
                                notification.setId(paymentNotification.getId());
                                notification.setMobile(user.getMobile());
                                notification.setMessage(paymentNotification.getMessage());
                                return notification;
                            });

                }).map(notification -> {
                    sender.sendNotification(notification);
                    return true;
                }).onErrorResume(t->Mono.just(false));

    }
}
