package ru.otus.spring_2020_11.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static ru.otus.spring_2020_11.hw14.config.MigrationJobConfig.BOOK_FILE_PARAMETER;
import static ru.otus.spring_2020_11.hw14.config.MigrationJobConfig.BOOK_READER_JOB;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final JobOperator operator;

    @ShellMethod(value = "migrate", key = {"migrate", "m"})
    public String migrate(@ShellOption String file) throws Exception {
        Long executionId = operator.start(BOOK_READER_JOB,
                BOOK_FILE_PARAMETER + "=" + file
        );

        return String.format("job %d started", executionId);
    }
}
