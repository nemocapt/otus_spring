package ru.otus.spring_2020_11.hw01.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.otus.spring_2020_11.hw01.domain.Answer;
import ru.otus.spring_2020_11.hw01.domain.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ExaminerImpl implements Examiner {
    private static final Answer ANSWER_STUB = new Answer("<empty answer>");

    @Getter
    private final List<Question> questions;
    private final Loader loader;
    private final String resource;

    public ExaminerImpl(Loader loader, String resource) {
        this.loader = loader;
        this.resource = resource;
        this.questions = loadQuestions();
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

    //region private
    private Question prepareQuestion(String line) {
        val phrases = line.split(",");

        if (phrases.length == 0 || phrases[0].isBlank()) {
            return null;
        }

        val answers = Arrays.stream(phrases)
                .skip(1)
                .filter(s -> !s.isBlank())
                .map(Answer::new)
                .collect(Collectors.toList());

        if (answers.isEmpty()) {
            answers.add(ANSWER_STUB);
        }

        return new Question(phrases[0], answers);
    }

    private List<Question> loadQuestions() {
        val lines = loader.getLines(resource);

        return lines.stream()
                .map(this::prepareQuestion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    //endregion
}
