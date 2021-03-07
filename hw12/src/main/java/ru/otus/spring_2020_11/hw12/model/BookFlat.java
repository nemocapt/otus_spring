package ru.otus.spring_2020_11.hw12.model;

import lombok.Data;

@Data
public class BookFlat {
    private String id;
    private String authorId;
    private String genre;
    private String title;
}
