package ru.otus.spring_2020_11.hw11.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring_2020_11.hw11.domain.Book;
import ru.otus.spring_2020_11.hw11.domain.Genre;
import ru.otus.spring_2020_11.hw11.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw11.repostitory.BookRepository;
import ru.otus.spring_2020_11.hw11.rest.dto.BookDto;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    @NonNull
    private final BookRepository bookRepository;
    @NonNull
    private final AuthorRepository authorRepository;

    @GetMapping("/api/books")
    public Flux<Book> showBooksAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/book/{id}")
    public Mono<Book> showBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @PostMapping("/api/book/{id}")
    public Mono<Book> updateBook(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
        return bookRepository.findById(id)
                .flatMap(
                        b -> authorRepository.findById(bookDto.getAuthorId())
                                .flatMap(a -> {
                                    b.setAuthor(a);
                                    b.setGenre(new Genre(bookDto.getGenre()));
                                    b.setTitle(bookDto.getTitle());

                                    return bookRepository.save(b);
                                })
                );
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBookById(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

    @Transactional
    @PutMapping("/api/book")
    public Mono<Book> insertBook(@RequestBody BookDto bookDto) {
        return Mono.just(new Book())
                .flatMap(
                        b -> authorRepository.findById(bookDto.getAuthorId())
                                .flatMap(a -> {
                                    b.setAuthor(a);
                                    b.setGenre(new Genre(bookDto.getGenre()));
                                    b.setTitle(bookDto.getTitle());

                                    return bookRepository.save(b);
                                })
                );
    }
}
