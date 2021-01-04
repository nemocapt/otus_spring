package ru.otus.spring_2020_11.hw06.dao;

import ru.otus.spring_2020_11.hw06.domain.Commentary;

import java.util.List;

public interface CommentaryDao {
    List<Commentary> getByBook(long bookId);

    void insert(Commentary commentary);
}
