package ru.otus.spring_2020_11.hw06.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
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
    @OneToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;
    @OneToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @Column
    private String title;
}
