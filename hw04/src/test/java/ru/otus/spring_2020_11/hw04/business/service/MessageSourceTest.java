package ru.otus.spring_2020_11.hw04.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring_2020_11.hw04.business.ExaminerShell;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageSourceTest {
    @Configuration
    @ComponentScan(
            basePackages =
                    "ru.otus.spring_2020_11.hw04.business.service," +
                    "ru.otus.spring_2020_11.hw04.starter.locale"
    )
    static class TestConfiguration {
    }

    @Autowired
    private MessageSource messageSource;

    @Test
    public void message_source_load_success() {
        assertThat(messageSource.getClass())
                .isEqualTo(ReloadableResourceBundleMessageSource.class);
    }
}


