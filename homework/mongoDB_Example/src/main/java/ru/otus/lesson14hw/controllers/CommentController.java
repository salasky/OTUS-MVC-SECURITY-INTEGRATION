package ru.otus.lesson14hw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.lesson14hw.domain.Book;
import ru.otus.lesson14hw.domain.Comment;
import ru.otus.lesson14hw.repository.BookRepository;
import ru.otus.lesson14hw.repository.CommentRepository;

import java.util.Collections;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping("/comment")
    public String author(@RequestParam String id, Model model){
        Comment comment = commentRepository.findById(id).get();
        model.addAttribute("comment", comment);
        return "comment";
    }

    @RequestMapping(value = "/editComment", method = RequestMethod.POST)
    public String editAuthor(@ModelAttribute Comment comment){
        commentRepository.save(comment);
        String id = comment.getId();
        return "redirect:/comment?id=" + id;
    }

    @RequestMapping("/commentsBook")
    public String commentsBook(@RequestParam String id, Model model){
        List<Comment> comments = Collections.emptyList();
        if(!bookRepository.findById(id).get().getComments().isEmpty()){
            comments = (List<Comment>)bookRepository.findById(id).get().getComments();
        }
        Comment comment = new Comment();
        model.addAttribute("idBook", id);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", comment);
        return "commentsBook";
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam String idBook, @ModelAttribute Comment comment){
        Book book = bookRepository.findById(idBook).get();
        comment = commentRepository.save(comment);
        book.setComment(comment);
        bookRepository.save(book);
        return "redirect:/book?id=" + idBook;
    }

    @RequestMapping("/deleteComment")
    public String deleteComment(@RequestParam String idBook, @ModelAttribute Comment comment){
        Book book = bookRepository.findById(idBook).get();
        book.getComments().remove(comment);
        bookRepository.save(book);
        return "redirect:/book?id=" + idBook;
    }
}
