package ru.otus.spring_2020_11.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw06.dao.CommentaryDao;
import ru.otus.spring_2020_11.hw06.domain.Commentary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class CommentaryJdbcDao implements CommentaryDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public void insert(Commentary commentary) {
        em.persist(commentary);
    }
}
