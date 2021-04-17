package ru.otus.spring_2020_11.hw16.repostitory;

import ru.otus.spring_2020_11.hw16.domain.Book;
import ru.otus.spring_2020_11.hw16.domain.Commentary;
import ru.otus.spring_2020_11.hw16.domain.Genre;

import java.util.List;

public interface BookRepositoryCustom {
    List<Genre> findAllGenre();

    void addCommentary(Book book, Commentary commentary);
}
