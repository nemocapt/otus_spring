package ru.otus.spring_2020_11.hw03.business.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring_2020_11.hw03.business.domain.Score;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class ExaminerImplTest {
    private static final String QUESTION = "qwe";
    private static final String ANSWER = "123";

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void examine_success() {
        val examiner = new ExaminerImpl(_createLoader("csv_test/tst_csv_01.csv"), new StringScreen("1"));

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
        val examiner = new ExaminerImpl(_createLoader("csv_test/tst_csv_empty.csv"), new StringScreen("1"));

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }

    @Test
    public void examine_absent_success() {
        val examiner = new ExaminerImpl(_createLoader("csv_test/tst_csv_absent.csv"), new StringScreen("1"));

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }

    @Test
    public void examine_comma_success() {
        val examiner = new ExaminerImpl(_createLoader("csv_test/tst_csv_02.csv"), new StringScreen("1"));

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }

    @Test
    public void choice_five_success() {
        val screen = new StringScreen("1");
        val examiner = new ExaminerImpl(_createLoader("csv_test/tst_csv_01.csv"), screen);

        examiner.examine();
        assertThat(screen.getText()).anyMatch(s -> s.contains(Score.FIVE.toString()));
    }

    @Test
    public void choice_four_success() {
        val screen = new StringScreen("2");
        val examiner = new ExaminerImpl(_createLoader("csv_test/tst_csv_01.csv"), screen);

        examiner.examine();
        assertThat(screen.getText()).anyMatch(s -> s.contains(Score.FOUR.toString()));
    }

    //region private
    private LoaderQuestion _createLoader(String resource) {
        return new LoaderQuestionCsv(resourceLoader.getResource(resource));
    }
    //endregion

    @RequiredArgsConstructor
    static class StringScreen implements Screen {
        @Getter
        private final List<String> text = new ArrayList<>();
        @NonNull
        private final String input;

        @Override
        public void print(String id, Object... args) {
            this.text.add(format(id, args));
        }

        @Override
        public String input() {
            return input;
        }
    }
}
