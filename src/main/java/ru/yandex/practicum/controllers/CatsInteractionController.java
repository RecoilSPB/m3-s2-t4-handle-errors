package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exceptions.HappinessOverflowException;
import ru.yandex.practicum.exceptions.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        // проверка happiness
        if (happiness == 10 || happiness > 10) {
            throw new HappinessOverflowException(happiness, "Осторожно, вы так избалуете котика!");
        }
        happiness++;
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }
        // проверка happiness
        if (happiness == 10 || happiness > 10) {
            throw new HappinessOverflowException(happiness, "Осторожно, вы так избалуете котика!");
        }

        happiness += count;
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @ExceptionHandler
    // в аргументах указывается родительское исключение
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectCount(final IncorrectCountException e) {
        return new ErrorResponse("Ошибка с параметром count.",
                e.getMessage()
        );
    }

    @ExceptionHandler
    // отлавливаем исключение RuntimeException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleError(final RuntimeException e) {
        // возвращаем сообщение об ошибке
        return new ErrorResponse("Произошла ошибка!",
                e.getMessage());
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