package ru.otus.spring_2020_11.hw02.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class GenericScreen  implements Screen {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String text) {
        System.out.println(text);
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }
}
