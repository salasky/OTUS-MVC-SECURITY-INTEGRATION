package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.entity.Book;
import ru.otus.entity.Comment;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.CommentRepository;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public Comment create(long bookId, String title, String body, String user) {
        final var targetBook = getTargetBookById(bookId);

        final var comment = Comment.builder()
                .commentTitle(title)
                .commentBody(body)
                .userName(user)
                .book(targetBook)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(long bookId) {
        final var targetBook = getTargetBookById(bookId);
        return commentRepository.findByBook(targetBook);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Book getTargetBookById(long id) {
        return bookService.findById(id).orElseThrow(
                () -> new NotFoundException("Отсутствует книга с id=" + id)
        );
    }
}
