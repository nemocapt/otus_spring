package ru.otus.spring_2020_11.hw04.business.domain;

import lombok.*;

import java.util.List;

@Value
@EqualsAndHashCode(
        of = {"text"}
)
public class Question {
    String text;
    List<Answer> answers;
    List<Integer> correctAnswers;
}
