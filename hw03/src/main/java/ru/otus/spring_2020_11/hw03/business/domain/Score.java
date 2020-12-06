package ru.otus.spring_2020_11.hw03.business.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
