package ru.otus.spring_2020_11.hw04.business;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring_2020_11.hw04.business.service.Examiner;

@ShellComponent
@RequiredArgsConstructor
public class ExaminerShell {
    @NonNull
    private Examiner examiner;

    @ShellMethod(value = "call examiner", key = {"exam"})
    public String examine(@ShellOption(defaultValue = "anonymous") String name) {
        System.out.printf(">>> student - [%s]\n", name);

        examiner.examine();

        return "<<< end of exam";
    }
}
