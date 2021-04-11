package ru.otus.spring_2020_11.hw15;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring_2020_11.hw15.butterfly.Butterfly;
import ru.otus.spring_2020_11.hw15.larva.Larva;

@MessagingGateway
public interface Transformation {
    @Gateway(requestChannel = "larvaChannel", replyChannel = "butterflyChannel")
    Butterfly transform(Larva larva);

}
