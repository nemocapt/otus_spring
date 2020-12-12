package ru.otus.spring_2020_11.hw02.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.spring_2020_11.hw02.domain.Answer;
import ru.otus.spring_2020_11.hw02.domain.Question;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class LoaderQuestionCsvTest {
    private static final Question SUCCESS_QUESTION = new Question(
            "qwe",
            Arrays.asList(new Answer("123")),
            Collections.emptyList()
    );

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void read_success() {
        val loader = new LoaderQuestionCsv(_createResource("csv_test/tst_csv_01.csv"));

        val entities = loader.getEntities();

        assertThat(entities).containsOnly(SUCCESS_QUESTION);
    }

    @Test
    public void read_empty_success() {
        val loader = new LoaderQuestionCsv(_createResource("csv_test/tst_csv_empty.csv"));

        val entities = loader.getEntities();

        assertThat(entities).isEmpty();
    }

    @Test
    public void read_absent_success() {
        val loader = new LoaderQuestionCsv(_createResource("csv_test/tst_csv_absent.csv"));

        val entities = loader.getEntities();

        assertThat(entities).isEmpty();
    }

    //region private
    private Resource _createResource(String resourcePath) {
        return resourceLoader.getResource(resourcePath);
    }
    //endregion
}
