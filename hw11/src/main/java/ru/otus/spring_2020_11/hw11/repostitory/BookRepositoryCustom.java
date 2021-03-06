package ru.otus.spring_2020_11.hw11.repostitory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring_2020_11.hw11.domain.Book;
import ru.otus.spring_2020_11.hw11.domain.Commentary;
import ru.otus.spring_2020_11.hw11.domain.Genre;

public interface BookRepositoryCustom {
    Flux<Genre> findAllGenre();

    Mono<Void> addCommentary(Book book, Commentary commentary);
}
