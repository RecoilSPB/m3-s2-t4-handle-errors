package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.exceptions.HappinessOverflowException;
import ru.yandex.practicum.exceptions.IncorrectCountException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    // в аргументах указывается родительское исключение
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectCount(final IncorrectCountException e) {
        return new ErrorResponse("Ошибка с параметром count.",
                e.getMessage()
        );
    }

    // метод handleHappinessOverflow
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHappinessOverflow(final HappinessOverflowException e) {
        return new ErrorResponse("Осторожно, вы так избалуете котика!",
                String.valueOf(e.getHappinessLevel())
        );
    }
}