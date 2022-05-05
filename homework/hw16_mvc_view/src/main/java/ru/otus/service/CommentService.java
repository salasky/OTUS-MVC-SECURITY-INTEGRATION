package ru.otus.service;

import ru.otus.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment create(long book, String title, String body, String user);
    List<Comment> findByBookId(long bookId);
    void deleteById(long id);
}
