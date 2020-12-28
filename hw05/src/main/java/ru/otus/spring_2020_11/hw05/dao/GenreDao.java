package ru.otus.spring_2020_11.hw05.dao;

import ru.otus.spring_2020_11.hw05.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(long id);
    List<Genre> getByName(String name);
    void insert(Genre genre);
    void deleteById(long id);
    List<Genre> getAll();
}
