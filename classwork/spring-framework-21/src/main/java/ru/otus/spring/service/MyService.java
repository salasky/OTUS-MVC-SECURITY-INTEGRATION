package ru.otus.spring.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public void onlyUser() {}

    public void onlyAdmin() {}
}
