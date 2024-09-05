package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness++;
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        happiness += count;
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    // в аргументах указывается родительское исключение
    public Map<String, String> handleIncorrectCount(final RuntimeException e) {
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
}