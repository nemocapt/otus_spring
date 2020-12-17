package ru.otus.spring_2020_11.hw04.starter.locale;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class MessageSourceProperties {
    private String locale;
}
