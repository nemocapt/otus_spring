package ru.otus.spring_2020_11.hw04.business.service;

import ru.otus.spring_2020_11.hw04.business.domain.Question;

import java.util.List;

public interface LoaderQuestion {
    List<Question> getEntities();
}
