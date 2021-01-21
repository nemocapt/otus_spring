package ru.otus.spring_2020_11.hw07.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring_2020_11.hw07.domain.Author;
import ru.otus.spring_2020_11.hw07.domain.Book;
import ru.otus.spring_2020_11.hw07.domain.Genre;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {
    Book findByTitle(String title);

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthor(Author author);
}
