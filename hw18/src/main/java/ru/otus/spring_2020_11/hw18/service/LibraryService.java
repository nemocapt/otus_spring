package ru.otus.spring_2020_11.hw18.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring_2020_11.hw18.domain.Author;
import ru.otus.spring_2020_11.hw18.domain.Book;
import ru.otus.spring_2020_11.hw18.domain.Genre;
import ru.otus.spring_2020_11.hw18.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw18.repostitory.BookRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryService {
    private static final String BACKEND = "backend";

    private static final Author AUTHOR_FAILED = new Author("failedName", "failedLastname");
    private static final Book BOOK_FAILED = new Book(
            AUTHOR_FAILED,
            new Genre("failedGenre"),
            "failedTitle"
    );

    @NonNull
    private final BookRepository bookRepository;
    @NonNull
    private final AuthorRepository authorRepository;

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallbackListBook")
    public List<Book> showAllBooks() {
        return bookRepository.findAll();
    }

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallbackBook")
    public Book showBookById(String id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @CircuitBreaker(name = BACKEND)
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @CircuitBreaker(name = BACKEND)
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallbackAuthor")
    public Author showAuthorById(String id) {
        return authorRepository.findById(id).get();
    }

    private Book fallbackBook(Exception e) {
        log.error("exception - {}", e.getMessage());

        return BOOK_FAILED;
    }

    private List<Book> fallbackListBook(Exception e) {
        log.error("exception - {}", e.getMessage());

        return Collections.singletonList(BOOK_FAILED);
    }

    private Author fallbackAuthor(Exception e) {
        log.error("exception - {}", e.getMessage());

        return AUTHOR_FAILED;
    }
}
