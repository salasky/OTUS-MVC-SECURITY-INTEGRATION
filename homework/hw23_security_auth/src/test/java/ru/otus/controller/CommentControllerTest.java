package ru.otus.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.controller.mapper.CommentMapper;
import ru.otus.dto.CommentDTO;
import ru.otus.entity.Author;
import ru.otus.entity.Book;
import ru.otus.entity.Comment;
import ru.otus.entity.Genre;
import ru.otus.service.CommentService;

import javax.sql.DataSource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование аутентификации пользователя для методов CommentController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    private static final long ID = 1L;
    private static final String NAME = "Песнь льда и Пламени";
    private static final Author AUTHOR = new Author(2, "Дж.Дж. Мартин");
    private static final Genre GENRE = new Genre(2, "Фэнтези");
    private static final Book TEST_BOOK = new Book(ID, NAME, AUTHOR, GENRE);
    private static final Comment COMMENT =
            new Comment(
                    1,
                    "Я хочу рассказать про супер книгу",
                    "Я думаю, что эта книга просто супер книга",
                    "юзер",
                    TEST_BOOK
            );
    private static final Comment NEW_COMMENT =
            new Comment(
                    2,
                    "Я хочу рассказать про супер книгу2",
                    "Я думаю, что эта книга просто супер книга2",
                    "юзер2",
                    TEST_BOOK
            );
    private static final List<Comment> COMMENTS = Lists.newArrayList(COMMENT, NEW_COMMENT);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Поиск всех комментариев по id книги")
    @Test
    public void findByBookId() throws Exception {
        given(commentService.findByBookId(1L)).willReturn(COMMENTS);

        mockMvc.perform(get("/find-comments?bookId=" + ID))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Получение страницы для создания комментария")
    @Test
    public void getBeforeCreate() throws Exception {
        mockMvc.perform(get("/create-comment?bookId=" + ID))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Создание комментария")
    @Test
    public void create() throws Exception {
        given(
                commentService.create(
                        TEST_BOOK.getId(),
                        COMMENT.getCommentTitle(),
                        COMMENT.getCommentBody(),
                        COMMENT.getUserName()
                )
        ).willReturn(COMMENT);

        mockMvc.perform(post("/create-comment").contentType(APPLICATION_FORM_URLENCODED)
                        .content(encode(CommentMapper.fromEntity(COMMENT))))
                .andExpect(redirectedUrl("/find-comments?bookId=" + TEST_BOOK.getId()));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Удаление комментария по id")
    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(get("/delete-comment?id=" + COMMENT.getId() + "&bookId=" + ID))
                .andExpect(redirectedUrl("/find-comments?bookId=" + ID));
    }

    private static String encode(CommentDTO comment) {
        return "id=" + URLEncoder.encode(Long.toString(comment.getId()), StandardCharsets.UTF_8) + "&" +
                "bookId=" + URLEncoder.encode(Long.toString(comment.getBookId()), StandardCharsets.UTF_8) + "&" +
                "title=" + URLEncoder.encode(comment.getTitle(), StandardCharsets.UTF_8) + "&" +
                "body=" + URLEncoder.encode(comment.getBody(), StandardCharsets.UTF_8) + "&" +
                "userName=" + URLEncoder.encode(comment.getUserName(), StandardCharsets.UTF_8);

    }

    @TestConfiguration
    public static class CommentControllerTestConfig {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .generateUniqueName(true)
                    .addScript("classpath:schema.sql")
                    .build();
        }
    }
}