package ru.otus.spring_2020_11.hw10.repostitory;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring_2020_11.hw10.domain.Book;
import ru.otus.spring_2020_11.hw10.domain.Genre;

import java.util.List;

public interface BookRepository extends BookRepositoryCustom, MongoRepository<Book, String> {
    Book findByTitle(String title);

    List<Book> findByGenre(Genre genre);
}
