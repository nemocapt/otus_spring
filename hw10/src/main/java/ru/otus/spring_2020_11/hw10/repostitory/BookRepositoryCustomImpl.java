package ru.otus.spring_2020_11.hw10.repostitory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring_2020_11.hw10.domain.Book;
import ru.otus.spring_2020_11.hw10.domain.Commentary;
import ru.otus.spring_2020_11.hw10.domain.Genre;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@Slf4j
@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public void addCommentary(Book book, Commentary commentary) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(book.getId())),
                new Update().push("commentaries", commentary),
                Book.class
        );
    }

    @Override
    public List<Genre> findAllGenre() {
        Aggregation aggregation = newAggregation(
                project().andExclude("_id").and(valueOfToArray("genre")).as("genre_entity"),
                project().and("genre_entity.v").as("name")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Genre.class).getMappedResults();
    }
}
