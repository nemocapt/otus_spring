package ru.otus.spring_2020_11.hw04.business.service;

import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.String.format;

@Service
public class GenericScreen implements Screen {
    @NonNull
    private final Locale locale;
    @NonNull
    private final MessageSource localizer;
    private final Scanner scanner = new Scanner(System.in);

    public GenericScreen(@Value("${app.locale}") String locale, MessageSource localizer) {
        this.locale = Locale.forLanguageTag(locale);
        this.localizer = localizer;
    }

    @Override
    public void print(String format, Object... args) {
        System.out.printf(format, rebuild(args));
        System.out.println();
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    //region private
    private Object[] rebuild(Object... args) {
        return Arrays.stream(args)
                .map(o -> {
                    if (o.getClass() == String.class && StringUtils.isNoneBlank((String) o)) {
                        val text = (String) o;
                        try {
                            return localizer.getMessage(text, null, locale);
                        } catch (NoSuchMessageException e) {
                            return text;
                        }
                    }

                    return o;
                })
                .toArray();
    }
    //endregion
}
