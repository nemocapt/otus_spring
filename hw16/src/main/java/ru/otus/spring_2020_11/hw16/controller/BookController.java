package ru.otus.spring_2020_11.hw16.controller;

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
import ru.otus.spring_2020_11.hw16.controller.model.BookFlat;
import ru.otus.spring_2020_11.hw16.domain.Book;
import ru.otus.spring_2020_11.hw16.domain.Genre;
import ru.otus.spring_2020_11.hw16.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw16.repostitory.BookRepository;

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
    public String updateBook(BookFlat bookFlat) {
        bookRepository.save(convertToBook(bookFlat));

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
    public String insertBook(BookFlat bookFlat) {
        bookRepository.save(convertToBook(bookFlat));

        return "redirect:/";
    }

    private Book convertToBook(BookFlat bookFlat) {
        val book = bookFlat.getId() == null ? new Book() : bookRepository.findById(bookFlat.getId()).get();
        val author = authorRepository.findById(bookFlat.getAuthorId()).get();
        val genre = new Genre(bookFlat.getGenre());

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(bookFlat.getTitle());

        return book;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotFound(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
