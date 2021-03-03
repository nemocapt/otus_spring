package ru.otus.spring_2020_11.hw11.changelogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import ru.otus.spring_2020_11.hw11.domain.Author;
import ru.otus.spring_2020_11.hw11.domain.Book;
import ru.otus.spring_2020_11.hw11.domain.Genre;
import ru.otus.spring_2020_11.hw11.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw11.repostitory.BookRepository;

import javax.annotation.PostConstruct;

@Component
@Order
public class InitMongoDbChangelog {
    @Autowired
    private ReactiveMongoTemplate template;
    @Autowired
    private AuthorRepository repo;
    @Autowired
    private BookRepository repository;

    private Author london;
    private Author adams;
    private Author corey;

    public void wipe() {
        template.getCollectionNames()
                .flatMap(c -> template.dropCollection(c))
                .blockLast();
    }

    public void initAuthors() {
        london = repo.save(new Author("Jack", "London")).block();
        adams = repo.save(new Author("Douglas", "Adams")).block();
        corey = repo.save(new Author("James", "Corey")).block();
    }

    public void initBooks() {
        repository.save(new Book(london, new Genre("adventure"), "White Fang")).block();
        repository.save(new Book(corey, new Genre("science fiction"), "The Expanse")).block();
        repository.save(new Book(adams, new Genre("comic science fiction"), "The Hitchhiker's Guide to the Galaxy")).block();
    }

    @PostConstruct
    public void init() {
        wipe();
        initAuthors();
        initBooks();
    }
}
