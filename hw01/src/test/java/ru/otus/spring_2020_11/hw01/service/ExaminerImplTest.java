package ru.otus.spring_2020_11.hw01.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class ExaminerImplTest {
    private static final String QUESTION = "qwe";
    private static final String ANSWER = "123";

    private final LoaderQuestion loaderQuestion = new LoaderQuestionCsv(new DefaultResourceLoader());

    @Test
    public void examine_success() {
        val examiner = new ExaminerImpl(loaderQuestion, "csv_test/tst_csv_01.csv");

        val question = examiner.getQuestions().get(0);
        val answers = question.getAnswers();

        assertThat(question.getText())
                .asString()
                .isEqualTo(QUESTION);
        assertThat(answers)
                .hasSize(1)
                .allMatch(a -> a.getText().equals(ANSWER));
    }

    @Test
    public void examine_empty_success() {
        val examiner = new ExaminerImpl(loaderQuestion, "csv_test/tst_csv_empty.csv");

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }

    @Test
    public void examine_absent_success() {
        val examiner = new ExaminerImpl(loaderQuestion, "csv_test/tst_csv_absent.csv");

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }

    @Test
    public void examine_comma_success() {
        val examiner = new ExaminerImpl(loaderQuestion, "csv_test/tst_csv_02.csv");

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }
}
