package ru.otus.spring_2020_11.hw06.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "commentGraph", attributeNodes = {
        @NamedAttributeNode(value = "book", subgraph = "book-subgraph"),
},
        subgraphs = {
                @NamedSubgraph(
                        name = "book-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("author"),
                                @NamedAttributeNode("genre")
                        }
                )
        }
)
@Entity
@Table(name = "commentary")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private String message;
}
