package ru.otus.spring.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Secured({"ROLE_USER","ROLE_USERADMIN"})
    public void onlyUser() {
        System.out.println("USSSEEESSR");
    }

    @Secured({"ROLE_USERADMIN"})
    public void onlyAdmin() {
        System.out.println("ADMINNN");
    }
}
