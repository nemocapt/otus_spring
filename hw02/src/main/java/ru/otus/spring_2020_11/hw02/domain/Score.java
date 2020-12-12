package ru.otus.spring_2020_11.hw02.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Score {
    FIVE("-five-"),
    FOUR("-four-"),
    THREE("-three-"),
    TWO("-two-");

    String score;
}
