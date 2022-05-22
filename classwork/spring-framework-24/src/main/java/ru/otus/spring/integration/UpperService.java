package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

@MessagingGateway
public interface UpperService {

    @SuppressWarnings("UnresolvedMessageChannel")
    @Gateway(requestChannel = "upper.input")
    Collection<String> upperStrings(Collection<String> str);
}
