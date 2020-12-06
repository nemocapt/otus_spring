package ru.otus.spring_2020_11.hw02.service;

import ru.otus.spring_2020_11.hw02.domain.Question;

import java.util.List;

public interface LoaderQuestion {
    List<Question> getEntities();
}
