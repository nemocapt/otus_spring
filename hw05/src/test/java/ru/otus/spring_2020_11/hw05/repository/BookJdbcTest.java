package ru.otus.spring_2020_11.hw05.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring_2020_11.hw05.domain.Author;
import ru.otus.spring_2020_11.hw05.domain.Book;
import ru.otus.spring_2020_11.hw05.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(BookJdbcDao.class)
public class BookJdbcTest {
    private static final Author AUTHOR = new Author(1, "a", "b");
    private static final Genre GENRE = new Genre(1, "g");

    @Autowired
    private BookJdbcDao bookDao;

    @Test
    void get_by_id_found() {
        val book = bookDao.getById(1);
        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "t")
                .hasFieldOrPropertyWithValue("author", AUTHOR)
                .hasFieldOrPropertyWithValue("genre", GENRE);
    }

    @Test
    void get_by_id_absent() {
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> bookDao.getById(2));
    }

    @Test
    void insert_title_dublicate() {
        assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> bookDao.insert(new Book(2, AUTHOR, GENRE, "t")));
    }

    @Test
    void delete_by_id() {
        assertThat(bookDao.getAll())
                .hasSize(1)
                .allMatch(b -> b.getId() == 1L);

        bookDao.deleteById(1L);

        assertThat(bookDao.getAll()).isEmpty();
    }

    @Test
    void delete_by_id_absent() {
        assertThat(bookDao.getAll())
                .hasSize(1)
                .allMatch(b -> b.getId() == 1L);

        bookDao.deleteById(2L);

        assertThat(bookDao.getAll())
                .hasSize(1)
                .allMatch(b -> b.getId() == 1L);
    }

    @Test
    void find_by_title() {
        val book = bookDao.getByTitle("t");

        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "t")
                .hasFieldOrPropertyWithValue("author", AUTHOR)
                .hasFieldOrPropertyWithValue("genre", GENRE);
    }

    @Test
    void find_by_genre() {
        bookDao.insert(new Book(AUTHOR, GENRE, "title"));

        val books = bookDao.getByGenre(GENRE);

        assertThat(books)
                .hasSize(2)
                .allMatch(b -> GENRE.equals(b.getGenre()))
                .anyMatch(b -> "t".equals(b.getTitle()))
                .anyMatch(b -> "title".equals(b.getTitle()));
    }
}
