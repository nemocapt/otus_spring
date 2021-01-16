package ru.otus.spring_2020_11.hw07.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring_2020_11.hw07.domain.Commentary;

public interface CommentaryDao extends JpaRepository<Commentary, Long> {
}
