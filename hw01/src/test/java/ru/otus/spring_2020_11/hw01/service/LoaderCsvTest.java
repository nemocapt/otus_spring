package ru.otus.spring_2020_11.hw01.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class LoaderCsvTest {
    private static final String SUCCESS_LINE = "qwe,123";

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void read_success() {
        val loader = new LoaderCsv(resourceLoader);

        val entities = loader.getEntities("csv_test/tst_csv_01.csv", String.class);

        assertThat(entities).containsOnly(SUCCESS_LINE);
    }

    @Test
    public void read_empty_success() {
        val loader = new LoaderCsv(resourceLoader);

        val entities = loader.getEntities("csv_test/tst_csv_empty.csv", String.class);

        assertThat(entities).isEmpty();
    }

    @Test
    public void read_absent_success() {
        val loader = new LoaderCsv(resourceLoader);

        val entities = loader.getEntities("csv_test/tst_csv_absent.csv", String.class);

        assertThat(entities).isEmpty();
    }
}
