package ru.otus.spring_2020_11.hw01.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(
        of = {"text"}
)
@ToString
public class Question {
    private static final Answer ANSWER_STUB = new Answer("<empty answer>");

    private final String text;
    private final List<Answer> answers;

    public Question(String line) {
        val phrases = line.split(",");

        this.text = phrases[0] == null ? "" : phrases[0];
        this.answers = prepareAnswers(phrases);
    }

    public String getQuestion() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    //region private
    private List<Answer> prepareAnswers(String[] phrases) {
        val answers = Arrays.stream(phrases)
                .skip(1)
                .filter(s -> !s.isBlank())
                .map(Answer::new)
                .collect(Collectors.toList());

        if (answers.isEmpty()) {
            answers.add(ANSWER_STUB);
        }

        return answers;
    }
    //endregion
}
