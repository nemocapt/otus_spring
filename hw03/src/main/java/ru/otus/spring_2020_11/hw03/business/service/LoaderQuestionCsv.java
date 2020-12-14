package ru.otus.spring_2020_11.hw03.business.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.spring_2020_11.hw03.business.domain.Answer;
import ru.otus.spring_2020_11.hw03.business.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoaderQuestionCsv implements LoaderQuestion {
    private static final Answer ANSWER_STUB = new Answer("<empty answer>");
    private static final Integer ANSWER_FIELD_START = 2;

    private final Resource resource;

    public LoaderQuestionCsv(@Value("${app.loader.resource:qa.csv}") Resource resource) {
        this.resource = resource;
    }

    @Override
    public List<Question> getEntities() {
        try {
            val entities = new ArrayList<Question>();

            for (val s : read()) {
                if (!s.isBlank() && !",".equals(s)) {
                    val phrases = s.split(",");

                    entities.add(parseQuestion(s));
                }
            }

            return entities;
        } catch (IOException e) {
            log.error(e.getMessage());

            return Collections.emptyList();
        }
    }

    //region private
    private List<String> read() throws IOException {
        val in = resource.getInputStream();
        val reader = new BufferedReader(new InputStreamReader(in));

        return reader.lines().collect(Collectors.toList());
    }

    private Question parseQuestion(String line) {
        if (line.isBlank() || ",".equals(line)) {
            log.warn("wrong format: empty line \"{}\"", line);
            return null;
        }

        val phrases = line.split(",");

        if (phrases.length < 2) {
            log.warn("wrong format: empty fields \"{}\"", line);
            return null;
        }

        val numbers = Arrays.stream(phrases[1].split(":"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return new Question(
                phrases[0] == null ? "" : phrases[0],
                prepareAnswers(phrases),
                numbers
        );
    }

    private List<Answer> prepareAnswers(String[] phrases) {
        val answers = Arrays.stream(phrases)
                .skip(ANSWER_FIELD_START)
                .filter(s -> !s.isBlank())
                .map(Answer::new)
                .collect(Collectors.toList());

        if (answers.isEmpty()) {
            answers.add(ANSWER_STUB);
        }

        return answers;
    }
    //endregion
}
