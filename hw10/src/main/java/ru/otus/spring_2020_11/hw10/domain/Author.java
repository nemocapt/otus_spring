package ru.otus.spring_2020_11.hw10.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "author")
public class Author {
    @Id
    private String id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
