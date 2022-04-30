package ru.otus.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Person;
import ru.otus.spring.repostory.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PersonController {

    private final PersonRepository repository;

    @Autowired
    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Person> persons = repository.findAll();
        model.addAttribute("persons", persons);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        Person person = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("p", person);
        return "edit";
    }

    @PostMapping("/edit")
    public String editPage(@RequestParam("id") int id, @RequestParam("name") String name, Model model) {
        if (id == 0) { // this is addition of new Person
            Person p = new Person(name);
            repository.save(p);
            return "redirect:/";
        }
        else {
            Person person = repository.findById(id).orElseThrow(NotFoundException::new);
            person.setName(name);
            repository.save(person);
        }
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        Person person = new Person("");
        model.addAttribute("p", person);
        return "edit";
    }
    
    @GetMapping("/delete")
    public String deletePage(@RequestParam("id") int id, Model model) {
        Person person = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("p", person);
        return "delete";
    }
    
    @PostMapping("/confirmdelete")
    public String confirmdeletePage(@RequestParam("id") int id, Model model) {
      Person person = repository.findById(id).orElseThrow(NotFoundException::new);
      repository.delete(person);
      return "redirect:/";
    }
    
    

    
}
