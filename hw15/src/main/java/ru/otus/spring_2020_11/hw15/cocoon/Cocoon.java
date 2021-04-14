package ru.otus.spring_2020_11.hw15.cocoon;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Cocoon {
    @NonNull
    @Getter
    private final String essence;
}
