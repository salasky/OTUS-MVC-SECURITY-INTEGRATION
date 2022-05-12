package ru.otus.spring.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void onlyUser() {
        System.out.println("USSSEEESSR");
    }

    public void onlyAdmin() {}
}
