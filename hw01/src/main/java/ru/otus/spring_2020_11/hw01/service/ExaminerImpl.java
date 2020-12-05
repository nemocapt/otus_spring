package ru.otus.spring_2020_11.hw01.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.otus.spring_2020_11.hw01.domain.Question;

import java.util.List;

@Slf4j
public class ExaminerImpl implements Examiner {

    @Getter
    private final List<Question> questions;
    private final LoaderQuestion loaderQuestion;
    private final String resource;

    public ExaminerImpl(LoaderQuestion loaderQuestion, String resource) {
        this.loaderQuestion = loaderQuestion;
        this.resource = resource;
        this.questions = loaderQuestion.getEntities(resource);
    }

    @Override
    public void examine() {
        questions.forEach(q -> {
            log.info("Q: {}?", q.getText());
            log.info("A:");

            q.getAnswers().forEach(a -> {
                log.info("\t* {}", a.getText());
            });
        });
    }
}
