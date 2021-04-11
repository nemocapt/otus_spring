package ru.otus.spring_2020_11.hw15.butterfly;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Butterfly {
    @NonNull
    @Getter
    private final String essence;
}
