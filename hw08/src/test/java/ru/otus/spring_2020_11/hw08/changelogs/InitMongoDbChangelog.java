package ru.otus.spring_2020_11.hw08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import ru.otus.spring_2020_11.hw08.domain.Author;
import ru.otus.spring_2020_11.hw08.domain.Book;
import ru.otus.spring_2020_11.hw08.domain.Genre;
import ru.otus.spring_2020_11.hw08.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw08.repostitory.BookRepository;

@ChangeLog(order = "001")
public class InitMongoDbChangelog {

    private Author a;
    private Author b;

    @ChangeSet(order = "000", id = "wipe", author = "nemo", runAlways = true)
    public void wipe(MongockTemplate template) {
        template.getDb().drop();

        final var titleIndex = new Index()
                .on("title", Sort.Direction.ASC)
                .named("title_index")
                .unique();
        final var genreIndex = new Index()
                .named("genre_index")
                .on("genre", Sort.Direction.ASC);

        template.indexOps(Book.class).ensureIndex(titleIndex);
        template.indexOps(Book.class).ensureIndex(genreIndex);
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "nemo", runAlways = true)
    public void initAuthors(AuthorRepository repo) {
        a = repo.save(new Author("a", "b"));
        b = repo.save(new Author("q", "w"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "nemo", runAlways = true)
    public void initBooks(BookRepository repository) {
        repository.save(new Book(a, new Genre("g"), "t"));
    }
}
