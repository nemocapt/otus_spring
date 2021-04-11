package ru.otus.spring_2020_11.hw15;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.spring_2020_11.hw15.butterfly.Butterfly;
import ru.otus.spring_2020_11.hw15.cocoon.Cocoon;
import ru.otus.spring_2020_11.hw15.larva.Larva;

@Slf4j
@SpringBootApplication
public class EntryPoint {
    public static void main(String[] args) {
        val context = SpringApplication.run(EntryPoint.class, args);
        val transformation = context.getBean(Transformation.class);

        val butterfly = transformation.transform(new Larva("i_believe_i_can_fly"));

        log.info("Butterfly {} is soaring", butterfly.getEssence());
    }

    @Bean
    public DirectChannel larvaChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public DirectChannel cocoonChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public DirectChannel butterflyChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow transform() {
        return IntegrationFlows
                .from("larvaChannel")
                .log("--> transform to cocoon")
                .transform(l -> new Cocoon(((Larva) l).getEssence()))
                .log("--< cocoon")

                .channel("cocoonChannel")

                .log("--> transform to butterfly")
                .transform(c -> new Butterfly(((Cocoon) c).getEssence()))
                .log("--< butterfly")

                .channel("butterflyChannel")

                .get();
    }
}
