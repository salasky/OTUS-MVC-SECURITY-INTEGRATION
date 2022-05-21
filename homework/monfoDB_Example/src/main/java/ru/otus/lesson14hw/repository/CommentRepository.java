package ru.otus.lesson14hw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson14hw.domain.Comment;


public interface CommentRepository extends MongoRepository<Comment, String> {
}
