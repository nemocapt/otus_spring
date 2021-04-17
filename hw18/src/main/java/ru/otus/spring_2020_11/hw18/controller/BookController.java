package ru.otus.spring_2020_11.hw18.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring_2020_11.hw18.controller.model.BookFlat;
import ru.otus.spring_2020_11.hw18.domain.Book;
import ru.otus.spring_2020_11.hw18.domain.Genre;
import ru.otus.spring_2020_11.hw18.service.LibraryService;

@Controller
@RequiredArgsConstructor
public class BookController {
    @NonNull
    private final LibraryService libraryService;

    @GetMapping("/")
    public String showBooksAll(Model model) {
        val books = libraryService.showAllBooks();
        model.addAttribute("books", books);

        return "list";
    }

    @GetMapping("/edit")
    public String showBookById(@RequestParam("id") String id, Model model) {
        val book = libraryService.showBookById(id);
        model.addAttribute("book", book);

        return "edit";
    }

    @Transactional
    @PostMapping("/edit")
    public String updateBook(BookFlat bookFlat) {
        libraryService.updateBook(convertToBook(bookFlat));

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBookById(@RequestParam("id") String id) {
        libraryService.deleteBookById(id);

        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAddBook() {
        return "add";
    }

    @Transactional
    @PostMapping("/add")
    public String insertBook(BookFlat bookFlat) {
        libraryService.updateBook(convertToBook(bookFlat));

        return "redirect:/";
    }

    private Book convertToBook(BookFlat bookFlat) {
        val book = bookFlat.getId() == null ? new Book() : libraryService.showBookById(bookFlat.getId());
        val author = libraryService.showAuthorById(bookFlat.getAuthorId());
        val genre = new Genre(bookFlat.getGenre());

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(bookFlat.getTitle());

        return book;
    }
}
