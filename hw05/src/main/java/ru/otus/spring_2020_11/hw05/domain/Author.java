package ru.otus.spring_2020_11.hw05.domain;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Author {
    @Setter
    private long id;
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
}
