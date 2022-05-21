package ru.otus.lesson14hw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.lesson14hw.domain.Genre;
import ru.otus.lesson14hw.repository.GenreRepository;

import java.util.List;

@Controller
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @RequestMapping("/genre")
    public String author(@RequestParam String id, Model model){
        Genre genre = genreRepository.findById(id).get();
        model.addAttribute("genre", genre);
        return "genre";
    }
    @RequestMapping("/genres")
    public String author(Model model){
        List<Genre> genres = genreRepository.findAll();
        Genre genre = new Genre();
        model.addAttribute("genres", genres);
        model.addAttribute("genre", genre);
        return "genres";
    }

    @RequestMapping(value = "deleteGenre", method = RequestMethod.POST)
    public String deleteGenre(@ModelAttribute Genre genre){
        genreRepository.delete(genre);
        return "redirect:/genres";
    }

    @RequestMapping(value = "createGenre", method = RequestMethod.POST)
    public String addGenre(@ModelAttribute Genre genre){
        genreRepository.save(genre);
        return "redirect:/genres";
    }

    @RequestMapping(value = "/editGenre", method = RequestMethod.POST)
    public String editAuthor(@ModelAttribute Genre genre){
        genreRepository.save(genre);
        return "redirect:/genres";
    }
}
