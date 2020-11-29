package ru.otus.spring_2020_11.hw01.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
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
    public List<String> getLines(String resourcePath) {
        try {
            InputStream in = getStream(resourcePath);

            return read(in);
        } catch (IOException e) {
            log.error(e.getMessage());

            return Collections.emptyList();
        }
    }

    //region private
    private InputStream getStream(String resourcePath) throws IOException {
        val resource = resourceLoader.getResource(resourcePath);

        return resource.getInputStream();
    }

    private List<String> read(InputStream in) {
        val reader = new BufferedReader(new InputStreamReader(in));

        return reader.lines().collect(Collectors.toList());
    }
    //endregion
}
