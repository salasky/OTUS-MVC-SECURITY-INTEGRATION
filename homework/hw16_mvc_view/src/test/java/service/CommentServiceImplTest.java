package service;

import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.entity.Author;
import ru.otus.entity.Book;
import ru.otus.entity.Comment;
import ru.otus.entity.Genre;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.CommentRepository;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.impl.CommentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Сервис для работы с комментариями к книгам")
@SpringBootTest(classes = {CommentServiceImpl.class})
class CommentServiceImplTest {
    private static final long ID = 1L;
    private static final String NAME = "Песнь льда и Пламени";
    private static final Author AUTHOR = new Author(2, "Дж.Дж. Мартин");
    private static final Genre GENRE = new Genre(2, "Фэнтези");
    private static final Book TEST_BOOK = new Book(1, NAME, AUTHOR, GENRE);
    private static final Comment COMMENT =
            new Comment(1, "Титул", "Тело коммента", "юзер", TEST_BOOK);
    private static final List<Comment> COMMENTS = Lists.newArrayList(COMMENT);

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        Mockito.when(bookService.findById(TEST_BOOK.getId()))
                .thenReturn(Optional.of(TEST_BOOK));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class)))
                .thenReturn(COMMENT);
        Mockito.when(commentRepository.findByBook(TEST_BOOK))
                .thenReturn(COMMENTS);
    }

    @DisplayName("Создание комментария (успех)")
    @Test
    void create_Success() {
        val comment =
                commentService.create(ID, COMMENT.getCommentTitle(), COMMENT.getCommentBody(), COMMENT.getUserName());
        assertThat(comment).usingRecursiveComparison().isEqualTo(COMMENT);
    }

    @DisplayName("Создание комментария (ошибка)")
    @Test
    void create_Fail() {
        Mockito.when(bookService.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                commentService.create(ID, COMMENT.getCommentTitle(), COMMENT.getCommentBody(), COMMENT.getUserName()))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("Найти комментарии по книге (успех)")
    @Test
    void find_Success() {
        val comments =
                commentService.findByBookId(ID);
        assertThat(comments).usingRecursiveComparison().isEqualTo(COMMENTS);
    }

    @DisplayName("Найти комментарии по книге (ошибка)")
    @Test
    void find_Fail() {
        Mockito.when(bookService.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                commentService.findByBookId(ID))
                .isInstanceOf(NotFoundException.class);
    }
}