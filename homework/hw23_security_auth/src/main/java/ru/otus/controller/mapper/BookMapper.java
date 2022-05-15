package ru.otus.controller.mapper;

import ru.otus.dto.BookDTO;
import ru.otus.entity.Book;


public class BookMapper {
    private BookMapper() {
    }

    public static BookDTO fromEntity(Book book) {
        return new BookDTO(
                book.getId(),
                book.getName(),
                book.getAuthor().getName(),
                book.getGenre().getName()
        );
    }
}
