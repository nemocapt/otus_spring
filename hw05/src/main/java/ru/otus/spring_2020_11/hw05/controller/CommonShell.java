package ru.otus.spring_2020_11.hw05.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring_2020_11.hw05.dao.AuthorDao;
import ru.otus.spring_2020_11.hw05.dao.BookDao;
import ru.otus.spring_2020_11.hw05.dao.GenreDao;
import ru.otus.spring_2020_11.hw05.domain.Author;
import ru.otus.spring_2020_11.hw05.domain.Book;
import ru.otus.spring_2020_11.hw05.domain.Genre;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommonShell {
    @NonNull
    private final BookDao bookDao;
    @NonNull
    private final AuthorDao authorDao;
    @NonNull
    private final GenreDao genreDao;

    @ShellMethod(value = "show all books", key = {"showbookall", "sba"})
    public String showBooksAll() {
        return bookDao.getAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "show book by id", key = {"showbookid", "sbi"})
    public String showBookById(@ShellOption long id) {
        return bookDao.getById(id).toString();
    }

    @ShellMethod(value = "insert book", key = {"insertbook", "ib"})
    public String insertBook(@ShellOption String title, @ShellOption long authorId, @ShellOption long genreid) {
        val author = authorDao.getById(authorId);
        val genre = genreDao.getById(genreid);

        val book = new Book(author, genre, title);
        bookDao.insert(book);

        return "inserted book:\n" + book.toString();
    }

    @ShellMethod(value = "show all authors", key = {"showauthorall", "saa"})
    public String showAuthorsAll() {
        return authorDao.getAll().stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "insert author", key = {"insertauthor", "ia"})
    public String insertAuthor(@ShellOption String firstName, @ShellOption String lastName) {
        val author = new Author(firstName, lastName);
        authorDao.insert(author);

        return "inserted author:\n" + author.toString();
    }

    @ShellMethod(value = "show all genres", key = {"showgenreall", "sga"})
    public String showGenresAll() {
        return genreDao.getAll().stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "insert genre", key = {"insertgenre", "ig"})
    public String insertGenre(@ShellOption String name) {
        val genre = new Genre(name);
        genreDao.insert(genre);

        return "inserted genre:\n" + genre.toString();
    }
}
