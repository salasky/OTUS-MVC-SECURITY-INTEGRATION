package ru.otus.lesson14hw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.lesson14hw.domain.Author;
import ru.otus.lesson14hw.domain.Book;
import ru.otus.lesson14hw.domain.Comment;
import ru.otus.lesson14hw.domain.Genre;
import ru.otus.lesson14hw.repository.AuthorRepository;
import ru.otus.lesson14hw.repository.BookRepository;
import ru.otus.lesson14hw.repository.CommentRepository;
import ru.otus.lesson14hw.repository.GenreRepository;

import java.util.Collection;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping("/books")
    public String books(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @RequestMapping("/book")
    public String book(@RequestParam String id, Model model){
        Book book = bookRepository.findById(id).get();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        return "book";
    }

    @RequestMapping(value = "/createBook", method = RequestMethod.GET)
    public String createBook(Model model){
        Book book = new Book();
        List<Genre> genres = genreRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "createBook";
    }

    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
    public String editBook(@ModelAttribute(value = "book") Book book){
        Collection<Author> authors = book.getAuthors();
        Collection<Comment> comments = book.getComments();
        authorRepository.saveAll(authors);
        commentRepository.saveAll(comments);
        bookRepository.save(book);
        String id = book.getId();
        return "redirect:/book?id=" + id;
    }

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute Book book){
        book = bookRepository.save(book);
        return "redirect:/book?id=" + book.getId();
    }

    @RequestMapping("/deleteBook")
    public String deleteBook(@RequestParam String id){
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @RequestMapping(value = "/deleteAuthorFromBook", method = RequestMethod.DELETE)
    public String deleteAuthorFromBook(@ModelAttribute Author author){
        String id = author.getId();
        return "redirect:/books";
    }
}
