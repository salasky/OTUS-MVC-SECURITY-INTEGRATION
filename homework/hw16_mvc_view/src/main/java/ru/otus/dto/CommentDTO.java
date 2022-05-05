package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.entity.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private long id;
    private long bookId;
    @NotEmpty(message = "Текст комментария не должен быть пустым")
    @Size(min = 10, message = "Длина комментария не может быть меньше 10 символов")
    private String title;
    @NotEmpty(message = "Текст комментария не должен быть пустым")
    @Size(min = 10, message = "Длина комментария не может быть меньше 10 символов")
    private String body;
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 40, message = "Имя пользователя должно быть длиной между 2 и 40 символами")
    private String userName;
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

