package ru.otus.spring_2020_11.hw05.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw05.dao.GenreDao;
import ru.otus.spring_2020_11.hw05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreJdbcDao implements GenreDao {
    @NonNull
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Map.of("id", id);

        return jdbc.queryForObject(
                "select id, name from genre where id = :id",
                params,
                new GenreMapper()
        );
    }

    @Override
    public List<Genre> getByName(String name) {
        Map<String, Object> params = Map.of("name", name);

        return jdbc.query(
                "select id, name from genre where name = :name",
                params,
                new GenreMapper()
        );
    }

    @Override
    public void insert(Genre genre) {
        val params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());

        val kh = new GeneratedKeyHolder();

        jdbc.update("insert into genre (`name`) values (:name)", params, kh);

        genre.setId(kh.getKey().longValue());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);

        jdbc.update("delete from genre where id = :id", params);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query(
                "select id, name from genre",
                new GenreMapper()
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");

            return new Genre(id, name);
        }
    }
}
