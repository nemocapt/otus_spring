package ru.otus.spring_2020_11.hw01.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.otus.spring_2020_11.hw01.domain.Question;

import java.util.List;

@Slf4j
public class ExaminerImpl implements Examiner {

    @Getter
    private final List<Question> questions;
    private final Loader loader;
    private final String resource;

    public ExaminerImpl(Loader loader, String resource) {
        this.loader = loader;
        this.resource = resource;
        this.questions = loader.getEntities(resource, Question.class);
    }

    @Override
    public void examine() {
        questions.forEach(q -> {
            log.info("Q: {}?", q.getQuestion());
            log.info("A:");

            q.getAnswers().forEach(a -> {
                log.info("\t* {}", a.getAnswer());
            });
        });
    }
}
