package ru.otus.spring_2020_11.hw05.domain;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Book {
    @Setter
    long id;
    @NonNull
    private final Author author;
    @NonNull
    private final Genre genre;
    @NonNull
    private final String title;
}
