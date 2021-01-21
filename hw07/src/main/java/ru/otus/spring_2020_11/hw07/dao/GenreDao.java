package ru.otus.spring_2020_11.hw07.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring_2020_11.hw07.domain.Genre;

import java.util.List;

@Repository
public interface GenreDao extends JpaRepository<Genre, Long> {
    List<Genre> findByName(String name);
}
