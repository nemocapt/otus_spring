package ru.otus.spring_2020_11.hw18;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongock
//@EnableCircuitBreaker
public class EntryPoint {
    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }
}
