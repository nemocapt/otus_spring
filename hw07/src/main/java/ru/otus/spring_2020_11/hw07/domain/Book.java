package ru.otus.spring_2020_11.hw07.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = "commentaries")
@RequiredArgsConstructor
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
    @NonNull
    private long id;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    @NonNull
    private Author author;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    @NonNull
    private Genre genre;

    @Column
    @NonNull
    private String title;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Commentary.class, mappedBy = "book", fetch = FetchType.LAZY)
    private List<Commentary> commentaries;
}
