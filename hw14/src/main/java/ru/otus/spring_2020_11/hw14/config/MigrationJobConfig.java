package ru.otus.spring_2020_11.hw14.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import ru.otus.spring_2020_11.hw14.domain.Author;
import ru.otus.spring_2020_11.hw14.domain.Book;
import ru.otus.spring_2020_11.hw14.domain.Genre;
import ru.otus.spring_2020_11.hw14.repostitory.AuthorRepository;
import ru.otus.spring_2020_11.hw14.repostitory.BookRepository;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MigrationJobConfig {
    public static final String BOOK_FILE_PARAMETER = "fileParameter";
    public static final String BOOK_READER_JOB = "bookReader";

    @NonNull
    private final JobBuilderFactory jobBuilderFactory;
    @NonNull
    private final StepBuilderFactory stepBuilderFactory;

    @StepScope
    @Bean
    public FlatFileItemReader<FlatBook> readerBook(@Value("#{jobParameters['" + BOOK_FILE_PARAMETER + "']}") String inputFileName) {
        return new FlatFileItemReaderBuilder<FlatBook>()
                .name("book_read_file")
                .resource(new FileSystemResource(inputFileName))
                .delimited()
                .names("firstName", "lastName", "genreName", "title")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(FlatBook.class);
                }})
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<FlatBook, Book> processor() {
        return new ItemProcessor<FlatBook, Book>() {
            @Override
            public Book process(FlatBook item) throws Exception {
                val book = new Book();

                book.setAuthor(new Author(item.getFirstName(), item.getLastName()));
                book.setGenre(new Genre(item.getGenreName()));
                book.setTitle(item.getTitle());

                return book;
            }
        };
    }

    @StepScope
    @Bean
    public CustomMongoWriter writeBook(BookRepository bookRepository, AuthorRepository authorRepository) {
        return new CustomMongoWriter(authorRepository, bookRepository);
    }

    @Bean
    public Step migrateStep(CustomMongoWriter writer, FlatFileItemReader<FlatBook> reader, ItemProcessor<FlatBook, Book> itemProcessor) {
        return stepBuilderFactory.get("migrateStep")
                .<FlatBook, Book>chunk(3)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(Object o) {
                        log.info("Прочитан объект - {}", o);
                    }

                    public void onReadError(Exception e) {
                        log.info("Ошибка чтения {}", e);
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        log.info("Запись списка - {}", list);
                    }

                    public void afterWrite(List list) {
                        log.info("Конец записи");
                    }

                    public void onWriteError(Exception e, List list) {
                        log.info("Ошибка записи, {}", e);
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        log.info("Начало обработки, {}", o);
                    }

                    public void afterProcess(Object o, Object o2) {
                        log.info("Преобразовали {} к {}", o, o2);
                    }

                    public void onProcessError(Object o, Exception e) {
                        log.info("Ошбка обработки, {}", e);
                    }
                })
                .build();
    }

    @Bean
    public Job migrate(Step step) {
        return jobBuilderFactory.get(BOOK_READER_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }
}
