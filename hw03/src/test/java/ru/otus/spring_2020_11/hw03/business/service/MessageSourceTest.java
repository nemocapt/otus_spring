package ru.otus.spring_2020_11.hw03.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    private MessageSource messageSource;

    @Test
    public void message_source_load_success() {
        assertThat(messageSource.getClass())
                .isEqualTo(ReloadableResourceBundleMessageSource.class);
    }
}
