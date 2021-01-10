package ru.otus.spring_2020_11.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw06.dao.AuthorDao;
import ru.otus.spring_2020_11.hw06.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorJdbcDao implements AuthorDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getByFirstnameAndLastname(String firstName, String lastName) {
        return em.createQuery(
                "select a from Author a where a.firstName = :fname and a.lastName = :lname",
                Author.class
        )
                .setParameter("fname", firstName)
                .setParameter("lname", lastName)
                .getResultList();
    }

    @Override
    public void insert(Author author) {
        em.merge(author);
    }

    @Override
    public void delete(Author author) {
        em.remove(author);

        em.flush();
        em.clear();
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }
}
