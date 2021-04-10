package ru.otus.spring_2020_11.hw14.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.item.ItemWriter;
import ru.otus.spring_2020_11.hw14.domain.Book;
import ru.otus.spring_2020_11.hw14.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw14.repostitory.BookRepository;

import java.util.List;

@RequiredArgsConstructor
public class CustomMongoWriter implements ItemWriter<Book> {
    @NonNull
    private final AuthorRepository authorRepository;
    @NonNull
    private final BookRepository bookRepository;

    @Override
    public void write(List<? extends Book> items) throws Exception {
        items.forEach(b -> {
            val author = b.getAuthor();
            val existAuthor = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
            b.setAuthor(existAuthor.orElseGet(() -> authorRepository.save(author)));

            bookRepository.save(b);
        });
    }
}
