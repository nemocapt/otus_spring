package ru.otus.spring_2020_11.hw10.rest.dto;

import lombok.Data;

@Data
public class BookDto {
    private String authorId;
    private String genre;
    private String title;
}
