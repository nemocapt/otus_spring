package ru.otus.spring_2020_11.hw01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring_2020_11.hw01.service.Examiner;

public class EntryPoint {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        Examiner examiner = context.getBean(Examiner.class);
        examiner.examine();
    }
}
