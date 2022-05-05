package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.BookDTO;
import ru.otus.exception.NotFoundException;
import ru.otus.service.BookService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class BookController {
    private static final String ERROR_MESSAGE_PREFIX = "Ошибка при выполнении: ";

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/create-book")
    public String create(Model model) {
        model.addAttribute("book", new BookDTO());
        return "create_book";
    }

    @Validated
    @PostMapping("/create-book")
    public String create(@Valid @ModelAttribute("book") BookDTO book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create_book";
        }
        try {
            bookService.create(
                    book.getBookName(),
                    book.getAuthorName(),
                    book.getGenreName()
            );
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ERROR_MESSAGE_PREFIX + ex.getMessage());
            return "create_book";
        }
        return "redirect:/";
    }

    @GetMapping("/update-book")
    public String update(@RequestParam("id") long id, Model model) {
        try {
            final var bookDTO = bookService.findById(id)
                    .map(BookDTO::fromEntity)
                    .orElseThrow(NotFoundException::new);
            model.addAttribute("book", bookDTO);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ERROR_MESSAGE_PREFIX + ex.getMessage());
        }
        return "edit_book";
    }

    @Validated
    @PostMapping("/update-book")
    public String update(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit_book";
        }
        try {
            bookService.update(
                    bookDTO.getBookId(),
                    bookDTO.getBookName(),
                    bookDTO.getAuthorName(),
                    bookDTO.getGenreName()
            );
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ERROR_MESSAGE_PREFIX + ex.getMessage());
            return "edit_book";
        }
        return "redirect:/";
    }

    @GetMapping("/delete-book")
    public String delete(@RequestParam("id") long id, Model model) {
        bookService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        final var books = bookService.findAll().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "books";
    }
}
