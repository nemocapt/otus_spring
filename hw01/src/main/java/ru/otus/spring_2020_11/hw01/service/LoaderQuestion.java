package ru.otus.spring_2020_11.hw01.service;

import ru.otus.spring_2020_11.hw01.domain.Question;

import java.util.List;

public interface LoaderQuestion {
    List<Question> getEntities(String resourcePath);
}
