package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private long bookId;
    @NotEmpty(message = "Название книги не должно быть пустым")
    private String bookName;
    @NotEmpty(message = "Имя автора не должно быть пустым")
    private String authorName;
    @NotEmpty(message = "Имя жанра не должно быть пустым")
    private String genreName;
}
