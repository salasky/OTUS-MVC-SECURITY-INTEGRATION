package ru.otus.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        this("Не найдено.");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
