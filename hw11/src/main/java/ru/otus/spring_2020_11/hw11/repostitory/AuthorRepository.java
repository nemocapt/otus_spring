package ru.otus.spring_2020_11.hw11.repostitory;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring_2020_11.hw11.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
