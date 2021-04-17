package ru.otus.spring_2020_11.hw16.infrastructure;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.spring_2020_11.hw16.repostitory.BookRepository;

@Component
@RequiredArgsConstructor
public class BookHealthIndicator implements HealthIndicator {
    @NonNull
    private final BookRepository repo;

    @Override
    public Health health() {
        try {
            val books = repo.findAll();

            return books.isEmpty() ?
                    Health.down()
                            .withDetail("message", "repo is empty")
                            .build()
                    :
                    Health.up()
                            .withDetail("message", "repo is up, books count - " + books.size())
                            .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("message", "repo is down")
                    .build();
        }
    }
}
