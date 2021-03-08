package ru.otus.spring_2020_11.hw13;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.spring_2020_11.hw13.domain.Book;
import ru.otus.spring_2020_11.hw13.domain.Genre;
import ru.otus.spring_2020_11.hw13.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw13.repostitory.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Slf4j
@DataMongoTest
@ComponentScan({
        "ru.otus.spring_2020_11.hw13.repository",
        "ru.otus.spring_2020_11.hw13.domain"
})
public class BookRepositoryTest {
    private static final Genre GENRE = new Genre("g");
    private static final String TITLE = "t";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void get_by_id_absent() {
        assertThat(bookRepository.findById("00000000000").isPresent())
                .isFalse();
    }

    @Test
    void insert_title_dublicate() {
        val author = authorRepository.findAll().get(0);

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> bookRepository.save(new Book(author, GENRE, TITLE)));
    }

    @Test
    void delete_by_id() {
        val books = bookRepository.findAll();

        assertThat(books).hasSize(1);

        val book = books.get(0);
        assertThat(book)
                .isNotNull();

        bookRepository.delete(book);

        assertThat(bookRepository.findAll()).isEmpty();
    }

    @Test
    void find_by_title() {
        val author = authorRepository.findAll().stream()
                .filter(a -> "a".equals(a.getFirstName()))
                .findFirst()
                .orElse(null);

        assertThat(author).isNotNull();

        val book = bookRepository.findByTitle(TITLE);

        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", TITLE)
                .hasFieldOrPropertyWithValue("author", author)
                .hasFieldOrPropertyWithValue("genre", GENRE);
    }

    @Test
    void find_by_genre() {
        val author = authorRepository.findAll().stream()
                .filter(a -> "a".equals(a.getFirstName()))
                .findFirst()
                .orElse(null);

        assertThat(author).isNotNull();

        bookRepository.save(new Book(author, GENRE, "title"));

        val books = bookRepository.findByGenre(GENRE);

        assertThat(books)
                .hasSize(2)
                .allMatch(b -> GENRE.equals(b.getGenre()))
                .anyMatch(b -> "title".equals(b.getTitle()));
    }
}
