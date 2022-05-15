package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
