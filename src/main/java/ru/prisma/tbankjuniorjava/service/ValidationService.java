package ru.prisma.tbankjuniorjava.service;

import org.springframework.stereotype.Service;
import ru.prisma.tbankjuniorjava.exception.ErrorType;
import ru.prisma.tbankjuniorjava.exception.validation.ValidationException;
import ru.prisma.tbankjuniorjava.model.TranslationRequest;

@Service
public class ValidationService {
    public void validateTranslationRequest(TranslationRequest request) {
        if (request.getText() == null || request.getText().trim().isEmpty()) {
            throw new ValidationException(ErrorType.BAD_ARGUMENT, "Text cannot be null or empty.");
        }

        if (request.getTargetLang() == null || request.getTargetLang().trim().isEmpty()) {
            throw new ValidationException(ErrorType.BAD_ARGUMENT, "Target language cannot be null or empty.");
        }

        if (request.getSourceLang() == null || request.getSourceLang().trim().isEmpty()) {
            throw new ValidationException(ErrorType.BAD_ARGUMENT, "Source language cannot be null or empty.");
        }
    }
}
