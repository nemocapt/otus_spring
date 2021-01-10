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
        return em.find(Genre.class, id);
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
    public void delete(Genre genre) {
        em.remove(genre);

        em.flush();
        em.clear();
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }
}
