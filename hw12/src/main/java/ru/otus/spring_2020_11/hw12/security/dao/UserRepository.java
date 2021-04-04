package ru.otus.spring_2020_11.hw12.security.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring_2020_11.hw12.security.dto.UserDb;

public interface UserRepository extends MongoRepository<UserDb, String> {
    UserDb findByLogin(String login);
}
