package ru.otus.spring_2020_11.hw01.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import ru.otus.spring_2020_11.hw01.domain.Answer;
import ru.otus.spring_2020_11.hw01.domain.Question;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class LoaderQuestionCsvTest {
    private static final Question SUCCESS_QUESTION = new Question("qwe", Arrays.asList(new Answer("123")));

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void read_success() {
        val loader = new LoaderQuestionCsv(resourceLoader);

        val entities = loader.getEntities("csv_test/tst_csv_01.csv");

        assertThat(entities).containsOnly(SUCCESS_QUESTION);
    }

    @Test
    public void read_empty_success() {
        val loader = new LoaderQuestionCsv(resourceLoader);

        val entities = loader.getEntities("csv_test/tst_csv_empty.csv");

        assertThat(entities).isEmpty();
    }

    @Test
    public void read_absent_success() {
        val loader = new LoaderQuestionCsv(resourceLoader);

        val entities = loader.getEntities("csv_test/tst_csv_absent.csv");

        assertThat(entities).isEmpty();
    }
}
