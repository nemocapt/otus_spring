package ru.otus.spring_2020_11.hw03.business;

import lombok.val;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import ru.otus.spring_2020_11.hw03.business.service.Examiner;
import ru.otus.spring_2020_11.hw03.starter.locale.MessageSourceAutoConfiguration;

@SpringBootApplication
@Import({MessageSourceAutoConfiguration.class})
public class EntryPoint {
    public static void main(String[] args) {
        val context = new SpringApplicationBuilder()
                .sources(EntryPoint.class)
                .banner(new IqBanner())
                .run(args);

        context.getBean(Examiner.class).examine();
    }
}
