package ru.otus.spring_2020_11.hw01.service;

import java.util.List;

public interface Loader {
    <T> List<T> getEntities(String resourcePath, Class<T> clazz);
}
