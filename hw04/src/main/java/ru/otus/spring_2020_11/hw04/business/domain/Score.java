package ru.otus.spring_2020_11.hw04.business.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Score {
    FIVE("-5-"),
    FOUR("-4-"),
    THREE("-3-"),
    TWO("-2-");

    String score;

    @Override
    public String toString() {
        return score;
    }
}
