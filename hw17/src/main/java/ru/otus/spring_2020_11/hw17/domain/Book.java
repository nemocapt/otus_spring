package ru.otus.spring_2020_11.hw17.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "commentaries")
@Document(collection = "book")
@CompoundIndexes({
        @CompoundIndex(name = "title_index", def = "{ 'title': 1 }", unique = true),
        @CompoundIndex(name = "genre_index", def = "{ 'genre': 1 }")
})
public class Book {
    @Id
    private String id;

    @Field
    @DBRef
    @NonNull
    private Author author;

    @Field
    @NonNull
    private Genre genre;

    @NonNull
    private String title;

    @Field
    private List<Commentary> commentaries = new ArrayList<>();
}
