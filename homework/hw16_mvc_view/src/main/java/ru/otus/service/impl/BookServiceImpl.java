package ru.otus.service.impl;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.entity.Book;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book create(String name, String authorName, String genreName) {
        Preconditions.checkArgument(
                StringUtils.isNoneEmpty(name),
                "имя книги не может быть пустым"
        );
        Preconditions.checkArgument(
                StringUtils.isNoneEmpty(authorName),
                "имя автора не может быть пустым"
        );
        Preconditions.checkArgument(
                StringUtils.isNoneEmpty(genreName),
                "имя жанра не может быть пустым"
        );
        final var author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new NotFoundException("отсутствует автор с именем " + authorName));
        final var genre = genreRepository.findByName(genreName)
                .orElseThrow(() -> new NotFoundException("отсутствует жанр с именем " + genreName));
        return bookRepository.save(new Book(name, author, genre));
    }

    @Override
    @Transactional
    public Book update(long id, String name, String authorName, String genreName) {
        Preconditions.checkArgument(
                StringUtils.isNoneEmpty(name),
                "имя книги не может быть пустым"
        );
        final var book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Отсутствует книга с id=" + id));
        book.setName(name);
        if (StringUtils.isNoneEmpty(authorName) && !Objects.equals(book.getAuthor().getName(), authorName)) {
            final var author = authorRepository.findByName(authorName)
                    .orElseThrow(() -> new NotFoundException("отсутствует автор с именем " + authorName));
            book.setAuthor(author);
        }
        if (StringUtils.isNoneEmpty(genreName) && !Objects.equals(book.getGenre().getName(), genreName)) {
            final var genre = genreRepository.findByName(genreName)
                    .orElseThrow(() -> new NotFoundException("отсутствует жанр с именем " + genreName));
            book.setGenre(genre);
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
}
