package ru.otus.controller.mapper;

import ru.otus.dto.CommentDTO;
import ru.otus.entity.Comment;

public class CommentMapper {
    private CommentMapper() {
    }

    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getBook().getId(),
                comment.getCommentTitle(),
                comment.getCommentBody(),
                comment.getUserName()
        );
    }
}
