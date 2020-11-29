package ru.otus.spring_2020_11.hw01.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(
        of = {"text"}
)
@ToString
public class Question {
    private final String text;
    private final List<Answer> answers;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public Question(String text, Answer... answers) {
        this(text, Arrays.stream(answers).collect(Collectors.toList()));
    }

    public String getQuestion() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
