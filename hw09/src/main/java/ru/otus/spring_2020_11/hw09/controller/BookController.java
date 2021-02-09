package ru.otus.spring_2020_11.hw09.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring_2020_11.hw09.domain.Book;
import ru.otus.spring_2020_11.hw09.domain.Genre;
import ru.otus.spring_2020_11.hw09.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw09.repostitory.BookRepository;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {
    @NonNull
    private final BookRepository bookRepository;
    @NonNull
    private final AuthorRepository authorRepository;

    @GetMapping("/")
    public String showBooksAll(Model model) {
        val books = bookRepository.findAll();
        model.addAttribute("books", books);

        return "list";
    }

    @GetMapping("/edit")
    public String showBookById(@RequestParam("id") String id, Model model) {
        val book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);

        return "edit";
    }

    @Transactional
    @PostMapping("/edit")
    public String updateBook(@RequestParam Map<String, String> map) {
        val book = bookRepository.findById(map.get("book_id")).get();
        val author = authorRepository.findById(map.get("author_id")).get();
        val genre = new Genre(map.get("genre_name"));

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(map.get("title"));

        bookRepository.save(book);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBookById(@RequestParam("id") String id) {
        bookRepository.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAddBook() {
        return "add";
    }

    @Transactional
    @PostMapping("/add")
    public String insertBook(@RequestParam Map<String, String> map) {
        val book = new Book();
        val author = authorRepository.findById(map.get("author_id")).get();
        val genre = new Genre(map.get("genre_name"));

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(map.get("title"));

        bookRepository.save(book);

        return "redirect:/";
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotFound(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
