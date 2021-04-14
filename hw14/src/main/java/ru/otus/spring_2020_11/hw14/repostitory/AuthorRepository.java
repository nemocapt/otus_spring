package ru.otus.spring_2020_11.hw14.repostitory;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring_2020_11.hw14.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
