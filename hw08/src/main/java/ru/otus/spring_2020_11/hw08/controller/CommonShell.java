package ru.otus.spring_2020_11.hw08.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring_2020_11.hw08.domain.Author;
import ru.otus.spring_2020_11.hw08.domain.Book;
import ru.otus.spring_2020_11.hw08.domain.Commentary;
import ru.otus.spring_2020_11.hw08.domain.Genre;
import ru.otus.spring_2020_11.hw08.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw08.repostitory.BookRepository;

import java.util.stream.Collectors;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class CommonShell {
    @NonNull
    private final BookRepository bookRepository;
    @NonNull
    private final AuthorRepository authorRepository;

    @ShellMethod(value = "show all books", key = {"showbookall", "sba"})
    public String showBooksAll() {
        return bookRepository.findAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "show book by id", key = {"showbookid", "sbi"})
    public String showBookById(@ShellOption String id) {
        return bookRepository.findById(id).toString();
    }

    @Transactional
    @ShellMethod(value = "insert book", key = {"insertbook", "ib"})
    public String insertBook(@ShellOption String title, @ShellOption String authorId, @ShellOption String genreName) {
        val author = authorRepository.findById(authorId).get();
        val genre = new Genre(genreName);
        val book = new Book(author, genre, title);

        bookRepository.save(book);

        return "inserted book:\n" + book.toString();
    }

    @ShellMethod(value = "show all authors", key = {"showauthorall", "saa"})
    public String showAuthorsAll() {
        return authorRepository.findAll().stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    @Transactional
    @ShellMethod(value = "insert author", key = {"insertauthor", "ia"})
    public String insertAuthor(@ShellOption String firstName, @ShellOption String lastName) {
        val author = new Author(firstName, lastName);

        authorRepository.save(author);

        return "inserted author:\n" + author.toString();
    }

    @ShellMethod(value = "show all genres", key = {"showgenreall", "sga"})
    public String showGenresAll() {
        return bookRepository.findAllGenre().stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    @Transactional
    @ShellMethod(value = "insert commentary", key = {"insertcommentary", "ic"})
    public String insertCommentary(@ShellOption String bookId, @ShellOption String message) {
        val book = bookRepository.findById(bookId).get();
        val commentary = new Commentary(message);

        bookRepository.addCommentary(book, commentary);

        return "inserted commentary:\n" + commentary.toString();
    }

    @Transactional
    @ShellMethod(value = "show all commentaries for book", key = {"showcoomentaries", "sc"})
    public String showCommentaries(@ShellOption String bookId) {
        val book = bookRepository.findById(bookId).get();

        return book.getCommentaries().stream()
                .map(Commentary::toString)
                .collect(Collectors.joining("\n"));
    }
}