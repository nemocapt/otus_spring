package ru.otus.spring_2020_11.hw05.dao;

import ru.otus.spring_2020_11.hw05.domain.Author;
import ru.otus.spring_2020_11.hw05.domain.Book;
import ru.otus.spring_2020_11.hw05.domain.Genre;

import java.util.List;

public interface BookDao {
    Book getById(long id);
    Book getByTitle(String title);
    List<Book> getByGenre(Genre genre);
    List<Book> getByAuthor(Author author);
    void insert(Book book);
    void deleteById(long id);
    List<Book> getAll();
}
