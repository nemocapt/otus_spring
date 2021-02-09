package ru.otus.spring_2020_11.hw09.repostitory;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring_2020_11.hw09.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
