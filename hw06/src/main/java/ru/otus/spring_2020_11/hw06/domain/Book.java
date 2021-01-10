package ru.otus.spring_2020_11.hw06.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "bookGraph", attributeNodes = {
        @NamedAttributeNode("author"),
        @NamedAttributeNode("genre")
})
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column
    private String title;
}
