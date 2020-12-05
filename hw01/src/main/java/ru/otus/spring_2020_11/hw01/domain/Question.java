package ru.otus.spring_2020_11.hw01.domain;

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(
        of = {"text"}
)
@ToString
@RequiredArgsConstructor
@Getter
public class Question {
    private final String text;
    private final List<Answer> answers;
}
