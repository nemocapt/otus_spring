package ru.otus.spring_2020_11.hw06.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring_2020_11.hw06.dao.AuthorDao;
import ru.otus.spring_2020_11.hw06.dao.BookDao;
import ru.otus.spring_2020_11.hw06.dao.CommentaryDao;
import ru.otus.spring_2020_11.hw06.dao.GenreDao;
import ru.otus.spring_2020_11.hw06.domain.Author;
import ru.otus.spring_2020_11.hw06.domain.Book;
import ru.otus.spring_2020_11.hw06.domain.Commentary;
import ru.otus.spring_2020_11.hw06.domain.Genre;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class CommonShell {
    EntityManager manager;
    @NonNull
    private final BookDao bookDao;
    @NonNull
    private final AuthorDao authorDao;
    @NonNull
    private final GenreDao genreDao;
    @NonNull
    private final CommentaryDao commentaryDao;

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

    @Transactional
    @ShellMethod(value = "insert book", key = {"insertbook", "ib"})
    public String insertBook(@ShellOption String title, @ShellOption long authorId, @ShellOption long genreid) {
        val author = authorDao.getById(authorId);
        val genre = genreDao.getById(genreid);

        val book = new Book(0, author, genre, title);
        bookDao.insert(book);

        return "inserted book:\n" + book.toString();
    }

    @ShellMethod(value = "show all authors", key = {"showauthorall", "saa"})
    public String showAuthorsAll() {
        return authorDao.getAll().stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }

    @Transactional
    @ShellMethod(value = "insert author", key = {"insertauthor", "ia"})
    public String insertAuthor(@ShellOption String firstName, @ShellOption String lastName) {
        val author = new Author(0, firstName, lastName);
        authorDao.insert(author);

        return "inserted author:\n" + author.toString();
    }

    @ShellMethod(value = "show all genres", key = {"showgenreall", "sga"})
    public String showGenresAll() {
        return genreDao.getAll().stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    @Transactional
    @ShellMethod(value = "insert genre", key = {"insertgenre", "ig"})
    public String insertGenre(@ShellOption String name) {
        val genre = new Genre(0, name);
        genreDao.insert(genre);

        return "inserted genre:\n" + genre.toString();
    }

    @Transactional
    @ShellMethod(value = "insert commentary", key = {"insertcommentary", "ic"})
    public String insertCommentary(@ShellOption long bookId, @ShellOption String message) {
        val book = bookDao.getById(bookId);
        val commentary = new Commentary(0, book, message);
        commentaryDao.insert(commentary);

        return "inserted commentary:\n" + commentary.toString();
    }

    @Transactional
    @ShellMethod(value = "show all commentaries for book", key = {"showcoomentaries", "sc"})
    public String showCommentaries(@ShellOption long bookId) {
        val book = bookDao.getById(bookId);

        return book.getCommentaries().stream()
                .map(Commentary::toString)
                .collect(Collectors.joining("\n"));
    }
}
