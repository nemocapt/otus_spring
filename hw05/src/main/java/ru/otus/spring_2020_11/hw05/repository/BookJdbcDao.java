package ru.otus.spring_2020_11.hw05.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw05.dao.BookDao;
import ru.otus.spring_2020_11.hw05.domain.Author;
import ru.otus.spring_2020_11.hw05.domain.Book;
import ru.otus.spring_2020_11.hw05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookJdbcDao implements BookDao {
    @NonNull
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Map.of("id", id);

        return jdbc.queryForObject(
                "select b.id, b.title, a.id a_id, a.fname, a.lname, g.id g_id, g.name " +
                        "from book b " +
                        "join author a on b.author_id = a.id " +
                        "join genre g on b.genre_id = g.id " +
                        "where b.id = :id",
                params,
                new BookMapper()
        );
    }

    @Override
    public Book getByTitle(String title) {
        Map<String, Object> params = Map.of("title", title);

        return jdbc.queryForObject(
                "select b.id, b.title, a.id a_id, a.fname, a.lname, g.id g_id, g.name " +
                        "from book b " +
                        "join author a on b.author_id = a.id " +
                        "join genre g on b.genre_id = g.id " +
                        "where b.title = :title",
                params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        Map<String, Object> params = Map.of("genre_id", genre.getId());

        return jdbc.query(
                "select b.id, b.title, a.id a_id, a.fname, a.lname, g.id g_id, g.name " +
                        "from book b " +
                        "join author a on b.author_id = a.id " +
                        "join genre g on b.genre_id = g.id " +
                        "where g.id = :genre_id",
                params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        Map<String, Object> params = Map.of("author_id", author.getId());

        return jdbc.query(
                "select b.id, b.title, a.id a_id, a.fname, a.lname, g.id g_id, g.name " +
                        "from book b " +
                        "join author a on b.author_id = a.id " +
                        "join genre g on b.genre_id = g.id " +
                        "where a.id = :author_id",
                params,
                new BookMapper()
        );
    }

    @Override
    public void insert(Book book) {
        val params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        val kh = new GeneratedKeyHolder();

        jdbc.update(
                "insert into book (`title`, `author_id`, `genre_id`) values (:title, :author_id, :genre_id)",
                params,
                kh
        );

        book.setId(kh.getKey().longValue());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of(
                "id", id
        );

        jdbc.update("delete from book where id = :id", params);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select b.id, b.title, a.id a_id, a.fname, a.lname, g.id g_id, g.name " +
                        "from book b " +
                        "join author a on b.author_id = a.id " +
                        "join genre g on b.genre_id = g.id ",
                new BookMapper()
        );
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long aid = rs.getLong("a_id");
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");

            long gid = rs.getLong("g_id");
            String name = rs.getString("name");

            long id = rs.getLong("id");
            String title = rs.getString("title");

            return new Book(id, new Author(aid, fname, lname), new Genre(gid, name), title);
        }
    }
}
