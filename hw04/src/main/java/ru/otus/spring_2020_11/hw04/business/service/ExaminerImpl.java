    package ru.otus.spring_2020_11.hw04.business.service;

    import lombok.Getter;
    import lombok.extern.slf4j.Slf4j;
    import lombok.val;
    import org.apache.commons.lang3.math.NumberUtils;
    import org.springframework.stereotype.Service;
    import ru.otus.spring_2020_11.hw04.business.domain.Choice;
    import ru.otus.spring_2020_11.hw04.business.domain.Question;
    import ru.otus.spring_2020_11.hw04.business.domain.Score;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.stream.Collectors;

    @Slf4j
    @Service
    public class ExaminerImpl implements Examiner {
        private static final String QUESTION_FORMAT = "- %s";
        private static final String ANSWER_FORMAT = "%4d. %s";
        private static final String TEXT_FORMAT = "%s";
        private static final String SCORE_FORMAT = "%s %s";

        private static final String NOTIFICATION_ANSWER_MESSAGE = "notify.input";
        private static final String DELIMITER_MESSAGE = "-----";
        private static final String SCORE_MESSAGE = "notify.score";
        private static final String QUESTIONS_EMPTY_MESSAGE = "error.questions.empty";

        @Getter
        private final List<Question> questions;
        private final Screen screen;

        public ExaminerImpl(LoaderQuestion loaderQuestion, Screen screen) {
            this.questions = loaderQuestion.getEntities();
            this.screen = screen;
        }

        @Override
        public void examine() {
            if (questions.isEmpty()) {
                notification(TEXT_FORMAT, QUESTIONS_EMPTY_MESSAGE);

                return;
            }

            notification(TEXT_FORMAT, NOTIFICATION_ANSWER_MESSAGE);
            screen.print(TEXT_FORMAT, DELIMITER_MESSAGE);

            final List<Choice> choices = new ArrayList<>();
            questions.forEach(q -> {
                choices.add(askQuestion(q));
                screen.print(TEXT_FORMAT, DELIMITER_MESSAGE);
            });

            val score = grading(choices);
            notification(SCORE_FORMAT, SCORE_MESSAGE, score);
        }

        //region private
        private void notification(String format, Object... args) {
            screen.print(TEXT_FORMAT, "***");
            screen.print(format, args);
            screen.print(TEXT_FORMAT, "***");
        }
        //endregion

        //region protected
        protected Choice askQuestion(Question question) {
            screen.print(QUESTION_FORMAT, question.getText());

            val answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                screen.print(ANSWER_FORMAT, i + 1, answers.get(i).getText());
            }

            val numbers = screen.input().split("[;,]");
            val choice = Arrays.stream(numbers)
                    .filter(NumberUtils::isCreatable)
                    .map(NumberUtils::createInteger)
                    .collect(Collectors.toList());

            return new Choice(question, choice);
        }

        protected Score grading(List<Choice> choices) {
            int errors = 0;

            for (Choice choice : choices) {
                if (!isCorrect(choice.getQuestion().getCorrectAnswers(), choice.getChoice())) {
                    errors++;
                }
            }

            return switch (errors) {
                case 0 -> Score.FIVE;
                case 1 -> Score.FOUR;
                case 2, 3 -> Score.THREE;
                default -> Score.TWO;
            };
        }

        protected boolean isCorrect(List<Integer> correct, List<Integer> verifiable) {
            return verifiable.containsAll(correct) && verifiable.size() == correct.size();
        }
        //endregion
    }
