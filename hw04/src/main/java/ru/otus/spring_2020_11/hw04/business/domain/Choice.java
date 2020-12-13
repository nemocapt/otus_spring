package ru.otus.spring_2020_11.hw04.business.domain;

import lombok.*;

import java.util.List;

@Value
@EqualsAndHashCode(
        of = {"question"}
)
public class Choice {
    Question question;
    List<Integer> choice;
}
