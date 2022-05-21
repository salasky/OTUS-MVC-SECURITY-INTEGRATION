package ru.otus.lesson14hw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson14hw.domain.Author;



public interface AuthorRepository extends MongoRepository<Author, String> {
}
