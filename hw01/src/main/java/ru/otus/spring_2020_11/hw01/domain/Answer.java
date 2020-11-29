package ru.otus.spring_2020_11.hw01.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Answer {
    private final String text;

    public String getAnswer() {
        return text;
    }
}
