package ru.otus.spring_2020_11.hw08.repostitory;

import ru.otus.spring_2020_11.hw08.domain.Book;
import ru.otus.spring_2020_11.hw08.domain.Commentary;
import ru.otus.spring_2020_11.hw08.domain.Genre;

import java.util.List;

public interface BookRepositoryCustom {
    List<Genre> findAllGenre();

    void addCommentary(Book book, Commentary commentary);
}
