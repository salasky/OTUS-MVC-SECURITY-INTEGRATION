package ru.otus.lesson14hw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson14hw.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
