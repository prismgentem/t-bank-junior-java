package ru.prisma.tbankjuniorjava.exception.validation;

import lombok.Getter;
import ru.prisma.tbankjuniorjava.exception.ErrorType;

@Getter
public class ValidationException extends RuntimeException {
    private final ErrorType errorType;

    public ValidationException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
}
