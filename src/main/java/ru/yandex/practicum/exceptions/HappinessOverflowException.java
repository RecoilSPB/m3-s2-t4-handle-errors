package ru.yandex.practicum.exceptions;

// добавьте необходимые поля, методы и наследование
public class HappinessOverflowException extends RuntimeException {
    private final int happinessLevel;

    public HappinessOverflowException(int happinessLevel, String message) {
        super(message);
        this.happinessLevel = happinessLevel;
    }

    public int getHappinessLevel() {
        return happinessLevel;
    }
}