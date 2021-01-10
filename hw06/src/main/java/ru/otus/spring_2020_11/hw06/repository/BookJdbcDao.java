package ru.otus.spring_2020_11.hw06.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw06.dao.BookDao;
import ru.otus.spring_2020_11.hw06.domain.Author;
import ru.otus.spring_2020_11.hw06.domain.Book;
import ru.otus.spring_2020_11.hw06.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookJdbcDao implements BookDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
//        val eg = em.getEntityGraph("bookGraph");
//
//        return em.createQuery(
//                "select b " +
//                        "from Book b " +
//                        "where b.id = :id",
//                Book.class
//        )
//                .setHint("javax.persistence.fetchgraph", eg)
//                .setParameter("id", id)
//                .getSingleResult();
    }

    @Override
    public Book getByTitle(String title) {
        val eg = em.getEntityGraph("bookGraph");

        return em.createQuery(
                "select b " +
                        "from Book b " +
                        "where b.title = :title",
                Book.class
        )
                .setHint("javax.persistence.fetchgraph", eg)
                .setParameter("title", title)
                .getSingleResult();
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        val eg = em.getEntityGraph("bookGraph");

        return em.createQuery(
                "select b " +
                        "from Book b " +
                        "where b.genre.id = :genre_id",
                Book.class
        )
                .setHint("javax.persistence.fetchgraph", eg)
                .setParameter("genre_id", genre.getId())
                .getResultList();
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        val eg = em.getEntityGraph("bookGraph");

        return em.createQuery(
                "select b " +
                        "from Book b " +
                        "where b.author.id = :author_id",
                Book.class
        )
                .setHint("javax.persistence.fetchgraph", eg)
                .setParameter("author_id", author.getId())
                .getResultList();
    }

    @Override
    public void insert(Book book) {
        em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);

        em.flush();
        em.clear();
    }

    @Override
    public List<Book> getAll() {
        val eg = em.getEntityGraph("bookGraph");

        return em.createQuery(
                "select b " +
                        "from Book b ",
                Book.class
        )
                .setHint("javax.persistence.fetchgraph", eg)
                .getResultList();
    }
}
