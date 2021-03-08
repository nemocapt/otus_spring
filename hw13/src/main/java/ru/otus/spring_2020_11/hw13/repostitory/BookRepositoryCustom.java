package ru.otus.spring_2020_11.hw13.repostitory;

import ru.otus.spring_2020_11.hw13.domain.Book;
import ru.otus.spring_2020_11.hw13.domain.Commentary;
import ru.otus.spring_2020_11.hw13.domain.Genre;

import java.util.List;

public interface BookRepositoryCustom {
    List<Genre> findAllGenre();

    void addCommentary(Book book, Commentary commentary);
}
