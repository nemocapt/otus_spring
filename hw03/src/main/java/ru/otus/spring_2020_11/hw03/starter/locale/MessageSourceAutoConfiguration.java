package ru.otus.spring_2020_11.hw03.starter.locale;

import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ConditionalOnProperty(name = "app.locale")
@EnableConfigurationProperties(MessageSourceProperties.class)
public class MessageSourceAutoConfiguration {
    @Bean
    @Primary
    public MessageSource configureMessageSource() {
        val messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }
}
