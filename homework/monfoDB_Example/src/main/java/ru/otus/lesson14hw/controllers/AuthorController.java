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
import ru.otus.lesson14hw.repository.AuthorRepository;
import ru.otus.lesson14hw.repository.BookRepository;

import java.util.Collections;
import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/author")
    public String author(@RequestParam String id, Model model){
        Author author = authorRepository.findById(id).get();
        model.addAttribute("author", author);
        return "author";
    }

    @RequestMapping("/authors")
    public String author(Model model){
        List<Author> authors = authorRepository.findAll();
        Author author = new Author();
        model.addAttribute("authors", authors);
        model.addAttribute("author",author);
        return "authors";
    }

    @RequestMapping(value = "/deleteAuthor", method = RequestMethod.POST)
    public String deleteAuthor(@RequestParam String id){
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }

    @RequestMapping(value = "/editAuthor", method = RequestMethod.POST)
    public String editAuthor(@ModelAttribute Author author){
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @RequestMapping(value = "/createAuthor", method = RequestMethod.POST)
    public String createAuthor(@ModelAttribute Author author){
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @RequestMapping("/authorsBook")
    public String authorsBook(@RequestParam String id, Model model){
        List<Author> authorsBook = Collections.emptyList();
        if(!bookRepository.findById(id).get().getAuthors().isEmpty()){
            authorsBook = (List<Author>)bookRepository.findById(id).get().getAuthors();
        }
        Author author = new Author();
        List<Author> authors  = (List<Author>) authorRepository.findAll();
        authors.removeAll(authorsBook);
        model.addAttribute("idBook", id);
        model.addAttribute("authorsBook", authorsBook);
        model.addAttribute("author", author);
        model.addAttribute("authors", authors);
        return "authorsBook";
    }

    @RequestMapping("/addAuthorInBook")
    public String addAuthorInBook(@ModelAttribute Author author, @RequestParam String idBook){
        Book book = bookRepository.findById(idBook).get();
        book.setAuthor(author);
        bookRepository.save(book);
        return "redirect:/book?id="+idBook;
    }

    @RequestMapping(value = "/deleteAuthorInBook", method = RequestMethod.POST)
    public String deleteAuthorInBook(@RequestParam String idBook, @ModelAttribute Author author){
        Book book = bookRepository.findById(idBook).get();
        book.getAuthors().remove(author);
        bookRepository.save(book);
        return "redirect:/book?id=" + idBook;
    }
}
