package ru.otus.spring_2020_11.hw12.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ru.otus.spring_2020_11.hw12.domain.Author;
import ru.otus.spring_2020_11.hw12.domain.Book;
import ru.otus.spring_2020_11.hw12.domain.Genre;
import ru.otus.spring_2020_11.hw12.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw12.repostitory.BookRepository;
import ru.otus.spring_2020_11.hw12.security.dao.UserRepository;
import ru.otus.spring_2020_11.hw12.security.dto.UserDb;

@ChangeLog(order = "001")
public class InitMongoDbChangelog {

    private Author london;
    private Author adams;
    private Author corey;

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
        london = repo.save(new Author("Jack", "London"));
        adams = repo.save(new Author("Douglas", "Adams"));
        corey = repo.save(new Author("James", "Corey"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "nemo", runAlways = true)
    public void initBooks(BookRepository repository) {
        repository.save(new Book(london, new Genre("adventure"), "White Fang"));
        repository.save(new Book(corey, new Genre("science fiction"), "The Expanse"));
        repository.save(new Book(adams, new Genre("comic science fiction"), "The Hitchhiker's Guide to the Galaxy"));
    }

    @ChangeSet(order = "003", id = "initUser", author = "nemo", runAlways = true)
    public void initUser(UserRepository repository) {
        repository.save(new UserDb("user", BCrypt.hashpw("pass", BCrypt.gensalt())));
    }
}
