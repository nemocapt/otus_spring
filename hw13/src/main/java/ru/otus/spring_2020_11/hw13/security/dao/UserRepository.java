package ru.otus.spring_2020_11.hw13.security.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring_2020_11.hw13.security.dto.UserDb;

public interface UserRepository extends MongoRepository<UserDb, String> {
    UserDb findByLogin(String login);
}
