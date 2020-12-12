package ru.otus.spring_2020_11.hw02.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring_2020_11.hw02.domain.Choice;
import ru.otus.spring_2020_11.hw02.domain.Question;
import ru.otus.spring_2020_11.hw02.domain.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
public class ExaminerImpl implements Examiner {
    private static final String QUESTION_FORMAT = "- %s";
    private static final String ANSWER_FORMAT = "%4d. %s";

    private static final String NOTIFICATION_ANSWER_MESSAGE = "Please enter correct answer number (or numbers delimited by \";\" or \",\").";
    private static final String DELIMITER_MESSAGE = "-----";
    private static final String SCORE_FORMAT = "The exam score is %s";
    private static final String QUESTIONS_EMPTY_MESSAGE = "Questions are not ready, please come back later.";

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
            notification(QUESTIONS_EMPTY_MESSAGE);

            return;
        }

        notification(NOTIFICATION_ANSWER_MESSAGE);
        screen.print(DELIMITER_MESSAGE);

        final List<Choice> choices = new ArrayList<>();
        questions.forEach(q -> {
            choices.add(askQuestion(q));
            screen.print(DELIMITER_MESSAGE);
        });

        val score = grading(choices);
        notification(format(SCORE_FORMAT, score));
    }

    //region private
    private void notification(String message) {
        screen.print("***");
        screen.print(message);
        screen.print("***");
    }

    private Choice askQuestion(Question question) {
        screen.print(format(QUESTION_FORMAT, question.getText()));

        val answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            screen.print(format(ANSWER_FORMAT, i + 1, answers.get(i).getText()));
        }

        val numbers = screen.input().split("[;,]");
        val choice = Arrays.stream(numbers)
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::createInteger)
                .collect(Collectors.toList());

        return new Choice(question, choice);
    }

    private Score grading(List<Choice> choices) {
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

    private boolean isCorrect(List<Integer> correct, List<Integer> verifiable) {
        return verifiable.containsAll(correct) && verifiable.size() == correct.size();
    }
    //endregion
}
