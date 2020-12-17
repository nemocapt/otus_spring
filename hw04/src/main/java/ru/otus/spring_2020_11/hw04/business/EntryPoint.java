package ru.otus.spring_2020_11.hw04.business;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import ru.otus.spring_2020_11.hw04.starter.locale.MessageSourceAutoConfiguration;

@SpringBootApplication
@Import({MessageSourceAutoConfiguration.class})
public class EntryPoint {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(EntryPoint.class)
                .banner(new IqBanner())
                .run(args);
    }
}
