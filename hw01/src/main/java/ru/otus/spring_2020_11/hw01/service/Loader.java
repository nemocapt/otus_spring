package ru.otus.spring_2020_11.hw01.service;

import java.util.List;

public interface Loader {
    List<String> getLines(String resourcePath);
}
