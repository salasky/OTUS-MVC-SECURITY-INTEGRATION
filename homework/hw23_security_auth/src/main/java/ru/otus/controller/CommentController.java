package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controller.mapper.CommentMapper;
import ru.otus.dto.CommentDTO;
import ru.otus.service.CommentService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class CommentController {
    private static final String ERROR_MESSAGE_PREFIX = "Ошибка при выполнении: ";

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/create-comment")
    public String create(@RequestParam("bookId") long bookId, Model model) {
        final var commentDTO = new CommentDTO();
        commentDTO.setBookId(bookId);
        model.addAttribute("comment", commentDTO);
        return "create_comment";
    }

    @Validated
    @PostMapping("/create-comment")
    public String create(@Valid @ModelAttribute("comment") CommentDTO comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create_comment";
        }
        try {
            commentService.create(
                    comment.getBookId(),
                    comment.getTitle(),
                    comment.getBody(),
                    comment.getUserName()
            );
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ERROR_MESSAGE_PREFIX + ex.getMessage());
            return "create_comment";
        }
        return "redirect:/find-comments?bookId=" + comment.getBookId();
    }

    @GetMapping("/find-comments")
    public String findByBookId(@RequestParam("bookId") long bookId, Model model) {
        final var comments = commentService.findByBookId(bookId).stream()
                .map(CommentMapper::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "comments";
    }

    @GetMapping("/delete-comment")
    public String delete(@RequestParam("id") long id, @RequestParam("bookId") long bookId) {
        commentService.deleteById(id);
        return "redirect:/find-comments?bookId=" + bookId;
    }
}
