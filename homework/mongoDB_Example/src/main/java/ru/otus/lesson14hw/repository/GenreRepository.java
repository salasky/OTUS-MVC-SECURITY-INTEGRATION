package ru.otus.lesson14hw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson14hw.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
