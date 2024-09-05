package ru.yandex.practicum.exceptions;

public class IncorrectCountException extends RuntimeException {
    public IncorrectCountException(String message) {
        super(message);
    }
} 