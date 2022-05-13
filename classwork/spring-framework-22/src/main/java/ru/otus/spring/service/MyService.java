package ru.otus.spring.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Secured({"ROLE_USER"})
    public void onlyUser() {}

    @Secured({"ADMIN"})
    public void onlyAdmin() {}
}
