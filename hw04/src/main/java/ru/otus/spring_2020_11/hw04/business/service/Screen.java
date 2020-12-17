package ru.otus.spring_2020_11.hw04.business.service;

public interface Screen {
    void print(String format, Object... args);
    String input();
}
