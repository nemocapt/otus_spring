package ru.otus.spring_2020_11.hw10.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring_2020_11.hw10.domain.Book;
import ru.otus.spring_2020_11.hw10.domain.Genre;
import ru.otus.spring_2020_11.hw10.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw10.repostitory.BookRepository;
import ru.otus.spring_2020_11.hw10.rest.dto.BookDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    @NonNull
    private final BookRepository bookRepository;
    @NonNull
    private final AuthorRepository authorRepository;

    @GetMapping("/api/books/")
    public List<Book> showBooksAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/book/")
    public Book showBookById(@RequestParam("id") String id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    @PostMapping("/api/book/")
    public Book updateBook(@RequestParam("id") String id, @RequestBody BookDto bookDto) {
        return bookRepository.save(
                convertToBook(bookDto, id)
        );
    }

    @DeleteMapping("/api/book/")
    public ResponseEntity<Void> deleteBookById(@RequestParam("id") String id) {
        bookRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("/api/book/")
    public Book insertBook(BookDto bookDto) {
        return bookRepository.save(convertToBook(bookDto, null));
    }

    private Book convertToBook(BookDto bookDto, String id) {
        val book = id == null ? new Book() : bookRepository.findById(id).get();
        val author = authorRepository.findById(bookDto.getAuthorId()).get();
        val genre = new Genre(bookDto.getGenre());

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(bookDto.getTitle());

        return book;
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleNotFound(Exception ex) {
        return ResponseEntity.badRequest().build();
    }
}
