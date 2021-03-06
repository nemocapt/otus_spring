package ru.otus.spring_2020_11.hw11.repostitory;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring_2020_11.hw11.domain.Book;
import ru.otus.spring_2020_11.hw11.domain.Genre;

public interface BookRepository extends BookRepositoryCustom, ReactiveMongoRepository<Book, String> {
    Mono<Book> findByTitle(String title);

    Flux<Book> findByGenre(Genre genre);
}
