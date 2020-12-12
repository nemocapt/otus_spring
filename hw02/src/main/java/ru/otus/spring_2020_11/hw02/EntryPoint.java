package ru.otus.spring_2020_11.hw02;

import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring_2020_11.hw02.service.Examiner;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class EntryPoint {
    public static void main(String[] args) {
        val context = new AnnotationConfigApplicationContext(EntryPoint.class);

        context.getBean(Examiner.class).examine();
    }
}
