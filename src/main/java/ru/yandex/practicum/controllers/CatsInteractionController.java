package ru.yandex.practicum.controllers;

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
    public Map<String, String> handleIncorrectCount(final IncorrectCountException e) {
        return Map.of(
                "error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage()
        );
    }

    @ExceptionHandler
    // отлавливаем исключение RuntimeException
    public Map<String, String> handleError(final RuntimeException e) {
        // возвращаем сообщение об ошибке
        return Map.of("error", "Произошла ошибка!",
                "errorMessage", e.getMessage());
    }

    // метод handleHappinessOverflow
    @ExceptionHandler
    public Map<String, String> handleHappinessOverflow(final HappinessOverflowException e) {
        return Map.of(
                "error", "Осторожно, вы так избалуете котика!",
                "happinessLevel", String.valueOf(e.getHappinessLevel())
        );
    }
}