package ru.otus.spring_2020_11.hw01.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.ResourceLoader;
import ru.otus.spring_2020_11.hw01.domain.Answer;
import ru.otus.spring_2020_11.hw01.domain.Question;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LoaderQuestionCsv implements LoaderQuestion {
    private static final Answer ANSWER_STUB = new Answer("<empty answer>");

    private final ResourceLoader resourceLoader;

    public LoaderQuestionCsv(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Question> getEntities(String resourcePath) {
        try {
            val entities = new ArrayList<Question>();

            for (val s : read(resourcePath)) {
                if (!s.isBlank() && !",".equals(s)) {
                    val phrases = s.split(",");

                    entities.add(new Question(
                            phrases[0] == null ? "" : phrases[0],
                            prepareAnswers(phrases)
                    ));
                }
            }

            return entities;
        } catch (IOException e) {
            log.error(e.getMessage());

            return Collections.emptyList();
        }
    }

    //region private
    private List<String> read(String resourcePath) throws IOException {
        val in = resourceLoader.getResource(resourcePath).getInputStream();
        val reader = new BufferedReader(new InputStreamReader(in));

        return reader.lines().collect(Collectors.toList());
    }

    private List<Answer> prepareAnswers(String[] phrases) {
        val answers = Arrays.stream(phrases)
                .skip(1)
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
