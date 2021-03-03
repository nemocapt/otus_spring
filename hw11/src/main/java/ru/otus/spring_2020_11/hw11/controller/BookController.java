package ru.otus.spring_2020_11.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {
    @GetMapping("/")
    public String showBooksAll() {
        return "list";
    }

    @GetMapping("/edit")
    public String showBookById(@RequestParam("id") String id) {
        return "edit";
    }

    @GetMapping("/add")
    public String showAddBook() {
        return "add";
    }
}
