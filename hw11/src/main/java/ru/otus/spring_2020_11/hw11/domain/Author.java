package ru.otus.spring_2020_11.hw11.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "author")
@CompoundIndex(name = "author_index", def = "{ 'firstName': 1, 'lastName': 1 }", unique = true)
public class Author {
    @Id
    private String id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
