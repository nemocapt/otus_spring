package ru.otus.spring_2020_11.hw06.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw06.dao.CommentaryDao;
import ru.otus.spring_2020_11.hw06.domain.Commentary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentaryJdbcDao implements CommentaryDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Commentary> getByBook(long bookId) {
        val eg = em.getEntityGraph("commentGraph");

        return em.createQuery(
                "select c from Commentary c where c.book.id = :book_id",
                Commentary.class
        )
                .setHint("javax.persistence.fetchgraph", eg)
                .setParameter("book_id", bookId)
                .getResultList();
    }

    @Override
    public void insert(Commentary commentary) {
        em.merge(commentary);
    }
}
