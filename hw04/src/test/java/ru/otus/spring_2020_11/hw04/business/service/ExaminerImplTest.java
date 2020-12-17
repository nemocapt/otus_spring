package ru.otus.spring_2020_11.hw04.business.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import ru.otus.spring_2020_11.hw04.business.domain.Answer;
import ru.otus.spring_2020_11.hw04.business.domain.Choice;
import ru.otus.spring_2020_11.hw04.business.domain.Question;
import ru.otus.spring_2020_11.hw04.business.domain.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ExaminerImplTest {
    private static final String QUESTION = "qwe";
    private static final String ANSWER = "123";

    private static final LoaderTestImpl GENERIC_LOADER = new LoaderTestImpl(
            _createQuestions(
                    _createQuestion(
                            "qwe",
                            _createAnswer("123"),
                            _createVariants(1)
                    )
            ));
    private static final LoaderTestImpl SCORED_LOADER = new LoaderTestImpl(
            _createQuestions(
                    _createQuestion(
                            "qwe",
                            _createAnswer("123"),
                            _createVariants(1)
                    ),
                    _createQuestion(
                            "asd",
                            _createAnswer("123"),
                            _createVariants(1)
                    ),
                    _createQuestion(
                            "zxc",
                            _createAnswer("123"),
                            _createVariants(1)
                    ),
                    _createQuestion(
                            "ewq",
                            _createAnswer("123"),
                            _createVariants(1)
                    ),
                    _createQuestion(
                            "dsa",
                            _createAnswer("123"),
                            _createVariants(1)
                    )
            ));
    private static final LoaderTestImpl EMPTY_LOADER = new LoaderTestImpl(_createQuestions());
    private static final StringScreen GENERIC_SCREEN = new StringScreen("1");

    @Test
    public void examine_success() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);

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
        val examiner = new ExaminerImpl(EMPTY_LOADER, GENERIC_SCREEN);

        val question = examiner.getQuestions();

        assertThat(question).hasSize(0);
    }

    @Test
    public void choice_five_success() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);

        examiner.examine();
        assertThat(GENERIC_SCREEN.getText()).anyMatch(s -> s.contains(Score.FIVE.toString()));
    }

    @Test
    public void choice_four_success() {
        val screen = new StringScreen("2");
        val examiner = new ExaminerImpl(GENERIC_LOADER, screen);

        examiner.examine();
        assertThat(screen.getText()).anyMatch(s -> s.contains(Score.FOUR.toString()));
    }

    @Test
    public void ask_questions_choice_one_success() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);
        val question = GENERIC_LOADER.getEntities().get(0);

        val choice = examiner.askQuestion(question);
        assertThat(choice.getChoice())
                .containsOnly(1);
    }

    @Test
    public void compare_variants_success() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);

        assertThat(examiner.isCorrect(asList(1, 2), asList(2, 1))).isTrue();
    }

    @Test
    public void compare_variants_not_enough_fail() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);

        assertThat(examiner.isCorrect(asList(1, 2), asList(1))).isFalse();
    }

    @Test
    public void compare_variants_too_much_fail() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);

        assertThat(examiner.isCorrect(asList(1, 2), asList(1, 2, 3))).isFalse();
    }

    @Test
    public void compare_variants_all_miss_fail() {
        val examiner = new ExaminerImpl(GENERIC_LOADER, GENERIC_SCREEN);

        assertThat(examiner.isCorrect(asList(1, 2), asList(5, 3))).isFalse();
    }

    @Test
    public void score_five() {
        val examiner = new ExaminerImpl(SCORED_LOADER, GENERIC_SCREEN);
        val grade = examiner.grading(
                _createChoices(SCORED_LOADER, 1, 1, 1, 1, 1)
        );

        assertThat(grade).isEqualTo(Score.FIVE);
    }

    @Test
    public void score_four() {
        val examiner = new ExaminerImpl(SCORED_LOADER, GENERIC_SCREEN);
        val grade = examiner.grading(
                _createChoices(SCORED_LOADER, 2, 1, 1, 1, 1)
        );

        assertThat(grade).isEqualTo(Score.FOUR);
    }

    @Test
    public void score_three() {
        val examiner = new ExaminerImpl(SCORED_LOADER, GENERIC_SCREEN);
        val grade = examiner.grading(
                _createChoices(SCORED_LOADER, 2, 2, 1, 1, 1)
        );

        assertThat(grade).isEqualTo(Score.THREE);
    }

    @Test
    public void score_three_double() {
        val examiner = new ExaminerImpl(SCORED_LOADER, GENERIC_SCREEN);
        val grade = examiner.grading(
                _createChoices(SCORED_LOADER, 2, 2, 2, 1, 1)
        );

        assertThat(grade).isEqualTo(Score.THREE);
    }

    @Test
    public void score_two() {
        val examiner = new ExaminerImpl(SCORED_LOADER, GENERIC_SCREEN);
        val grade = examiner.grading(
                _createChoices(SCORED_LOADER, 2, 2, 2, 2, 1)
        );

        assertThat(grade).isEqualTo(Score.TWO);
    }

    @Test
    public void score_totally_two() {
        val examiner = new ExaminerImpl(SCORED_LOADER, GENERIC_SCREEN);
        val grade = examiner.grading(
                _createChoices(SCORED_LOADER, 2, 2, 2, 2, 2)
        );

        assertThat(grade).isEqualTo(Score.TWO);
    }

    //region private
    static private Question _createQuestion(String text, List<Answer> answers, List<Integer> variants) {
        return new Question(text, answers, variants);
    }

    static private List<Question> _createQuestions(Question... questions) {
        return asList(questions);
    }

    static private List<Answer> _createAnswer(String... answers) {
        return Arrays.stream(answers)
                .map(Answer::new)
                .collect(Collectors.toList());
    }

    static private List<Integer> _createVariants(Integer... variants) {
        return asList(variants);
    }

    static private List<Choice> _createChoices(LoaderTestImpl loader, Integer... choice) {
        val questions = loader.getEntities();
        val result = new ArrayList<Choice>();

        for (int i = 0; i < questions.size(); i++) {
            result.add(new Choice(questions.get(i), asList(choice[i])));
        }

        return result;
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

    @RequiredArgsConstructor
    static class LoaderTestImpl implements LoaderQuestion {
        @NonNull
        private final List<Question> questions;

        @Override
        public List<Question> getEntities() {
            return questions;
        }


    }
}
