package ru.otus.spring_2020_11.hw07;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.spring_2020_11.hw07.dao.BookDao;
import ru.otus.spring_2020_11.hw07.domain.Author;
import ru.otus.spring_2020_11.hw07.domain.Book;
import ru.otus.spring_2020_11.hw07.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Slf4j
@DataJpaTest
//@Import(BookDao.class)
public class BookJdbcTest {
    private static final Author AUTHOR = new Author(1, "a", "b");
    private static final Genre GENRE = new Genre(1, "g");

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDao bookDao;

    @Test
    void get_by_id_found() {
        val book = bookDao.findById(1L).get();
        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "t")
                .hasFieldOrPropertyWithValue("author", AUTHOR)
                .hasFieldOrPropertyWithValue("genre", GENRE);
    }

    @Test
    void get_by_id_absent() {
        assertThat(bookDao.findById(2L).isPresent())
                .isFalse();
    }

    @Test
    void insert_title_dublicate() {
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> bookDao.save(new Book(2, AUTHOR, GENRE, "t")));
    }

    @Test
    void delete_by_id() {
        val books = bookDao.findAll();
        assertThat(books)
                .hasSize(1)
                .allMatch(b -> b.getId() == 1L);

        bookDao.delete(books.get(0));

        assertThat(bookDao.findAll()).isEmpty();
    }

    @Test
    void find_by_title() {
        val book = bookDao.findByTitle("t");

        assertThat(book)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "t")
                .hasFieldOrPropertyWithValue("author", AUTHOR)
                .hasFieldOrPropertyWithValue("genre", GENRE);
    }

    @Test
    void find_by_genre() {
        bookDao.save(new Book(0, AUTHOR, GENRE, "title"));

        val books = bookDao.findByGenre(GENRE);

        assertThat(books)
                .hasSize(2)
                .allMatch(b -> GENRE.equals(b.getGenre()))
                .anyMatch(b -> "t".equals(b.getTitle()))
                .anyMatch(b -> "title".equals(b.getTitle()));
    }
}
