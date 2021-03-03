package ru.otus.spring_2020_11.hw11.repostitory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring_2020_11.hw11.domain.Book;
import ru.otus.spring_2020_11.hw11.domain.Commentary;
import ru.otus.spring_2020_11.hw11.domain.Genre;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@Slf4j
@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Void> addCommentary(Book book, Commentary commentary) {
        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(book.getId())),
                new Update().push("commentaries", commentary),
                Book.class
        ).then();
    }

    @Override
    public Flux<Genre> findAllGenre() {
        Aggregation aggregation = newAggregation(
                project().andExclude("_id").and(valueOfToArray("genre")).as("genre_entity"),
                project().and("genre_entity.v").as("name")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Genre.class);
    }
}
