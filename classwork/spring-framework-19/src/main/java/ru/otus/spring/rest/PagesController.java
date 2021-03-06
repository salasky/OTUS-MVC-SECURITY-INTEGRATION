package ru.otus.spring.rest;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage(Model model) {
        UserDetails userDetails= (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        model.addAttribute("userss",userDetails.getUsername());
        return "authenticated";
    }
}
