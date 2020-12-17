package ru.otus.spring_2020_11.hw04.business;

import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class IqBanner implements Banner {
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(
                yellow(" _ ") + white("_____ \n") +
                yellow("(_)") + white("  _  |\n") +
                yellow(" _") + white("| | | |\n") +
                yellow("| |") + white(" | | |\n") +
                yellow("| ") + white("\\ \\/' /\n") +
                yellow("|_|") + white("\\_/\\_\\\n")
        );
    }

    //region private
    private String yellow(String line) {
        return AnsiOutput.toString(AnsiColor.BRIGHT_YELLOW, line, AnsiColor.DEFAULT);
    }

    private String white(String line) {
        return AnsiOutput.toString(AnsiColor.BRIGHT_WHITE, line, AnsiColor.DEFAULT);
    }
    //endregion
}
