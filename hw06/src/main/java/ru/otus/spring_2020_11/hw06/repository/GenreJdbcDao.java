package ru.otus.spring_2020_11.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw06.dao.GenreDao;
import ru.otus.spring_2020_11.hw06.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreJdbcDao implements GenreDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre getById(long id) {
        return em.createQuery(
                "select g from Genre g where g.id = :id",
                Genre.class
        )
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Genre> getByName(String name) {
        return em.createQuery(
                "select g from Genre g where g.name = :name",
                Genre.class
        )
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void insert(Genre genre) {
        em.merge(genre);
    }

    @Override
    public void deleteById(long id) {
        em.createQuery("delete from Genre g where g.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }
}
