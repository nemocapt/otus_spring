package ru.otus.spring_2020_11.hw11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class EntryPoint {
    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }
}
