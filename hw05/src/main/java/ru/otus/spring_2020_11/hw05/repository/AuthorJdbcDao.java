package ru.otus.spring_2020_11.hw05.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw05.dao.AuthorDao;
import ru.otus.spring_2020_11.hw05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorJdbcDao implements AuthorDao {
    @NonNull
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Map.of("id", id);

        return jdbc.queryForObject(
                "select id, fname, lname from author where id = :id",
                params,
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> getByFirstnameAndLastname(String firstName, String lastName) {
        Map<String, Object> params = Map.of(
                "fname", firstName,
                "lname", lastName
        );

        return jdbc.query(
                "select id, fname, lname from author where fname = :fname and lname = :lname",
                params,
                new AuthorMapper()
        );
    }

    @Override
    public void insert(Author author) {
        val params = new MapSqlParameterSource();
        params.addValue("fname", author.getFirstName());
        params.addValue("lname", author.getLastName());

        val kh = new GeneratedKeyHolder();

        jdbc.update(
                "insert into author (`fname`, `lname`) values (:fname, :lname)",
                params,
                kh
        );

        author.setId(kh.getKey().longValue());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);

        jdbc.update(
                "delete from author where id = :id",
                params
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, fname, lname from author", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");

            return new Author(id, fname, lname);
        }
    }
}
