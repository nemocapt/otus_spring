package ru.otus.spring_2020_11.hw01.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LoaderCsv implements Loader {
    private final ResourceLoader resourceLoader;

    public LoaderCsv(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public <T> List<T> getEntities(String resourcePath, Class<T> clazz) {
        try {
            val constructor = clazz.getConstructor(String.class);
            val entities = new ArrayList<T>();

            for (val s : read(resourcePath)) {
                if (!s.isBlank() && !",".equals(s)) {
                    entities.add(constructor.newInstance(s));
                }
            }

            return entities;
        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
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
    //endregion
}
