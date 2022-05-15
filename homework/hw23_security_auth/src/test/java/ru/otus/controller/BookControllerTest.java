package ru.otus.controller;

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
import ru.otus.controller.mapper.BookMapper;
import ru.otus.dto.BookDTO;
import ru.otus.entity.Author;
import ru.otus.entity.Book;
import ru.otus.entity.Genre;
import ru.otus.service.BookService;

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


@DisplayName("Тестирование аутентификации пользователя для методов BookController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    private static final long ID = 1;
    private static final String NAME = "Песнь льда и Пламени";
    private static final String NEW_NAME = "Игра Престолов";
    private static final Author AUTHOR = new Author(2, "Дж.Дж. Мартин");
    private static final Genre GENRE = new Genre(2, "Фэнтези");
    private static final Book TEST_BOOK = new Book(ID, NAME, AUTHOR, GENRE);
    private static final Book NEW_TEST_BOOK = new Book(ID + 1, NEW_NAME, AUTHOR, GENRE);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Получение станицы для создания книги")
    @Test
    public void getBeforeCreate() throws Exception {
        mockMvc.perform(get("/create-book"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Создание книги")
    @Test
    public void create() throws Exception {
        given(
                bookService.create(
                        TEST_BOOK.getName(),
                        TEST_BOOK.getAuthor().getName(),
                        TEST_BOOK.getGenre().getName()
                )
        ).willReturn(TEST_BOOK);
        List<Book> books = List.of(TEST_BOOK, NEW_TEST_BOOK);
        given(bookService.findAll()).willReturn(books);

        mockMvc.perform(post("/create-book")
                        .contentType(APPLICATION_FORM_URLENCODED).content(encode(BookMapper.fromEntity(TEST_BOOK))))
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Обновление книги")
    @Test
    public void update() throws Exception {
        given(
                bookService.update(
                        TEST_BOOK.getId(),
                        TEST_BOOK.getName(),
                        TEST_BOOK.getAuthor().getName(),
                        TEST_BOOK.getGenre().getName()
                )
        ).willReturn(TEST_BOOK);

        mockMvc.perform(post("/update-book")
                        .contentType(APPLICATION_FORM_URLENCODED).content(encode(BookMapper.fromEntity(TEST_BOOK))))
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Удаление книги по id")
    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(get("/delete-book?id=1"))
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Поиск всех книг")
    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    private static String encode(BookDTO book) {
        return "bookId=" + URLEncoder.encode(Long.toString(book.getBookId()), StandardCharsets.UTF_8) + "&" +
                "bookName=" + URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8) + "&" +
                "authorName=" + URLEncoder.encode(book.getAuthorName(), StandardCharsets.UTF_8) + "&" +
                "genreName=" + URLEncoder.encode(book.getGenreName(), StandardCharsets.UTF_8);

    }

    @TestConfiguration
    public static class BookControllerTestConfig {
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