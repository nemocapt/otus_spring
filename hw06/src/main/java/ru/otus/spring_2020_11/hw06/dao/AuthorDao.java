package ru.otus.spring_2020_11.hw06.dao;

import ru.otus.spring_2020_11.hw06.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(long id);

    List<Author> getByFirstnameAndLastname(String firstName, String lastName);

    void insert(Author author);

    void delete(Author author);

    List<Author> getAll();
}
