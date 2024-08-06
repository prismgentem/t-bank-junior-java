package ru.prisma.tbankjuniorjava.service;

import org.springframework.stereotype.Component;
import ru.prisma.tbankjuniorjava.model.TranslationRequest;
import ru.prisma.tbankjuniorjava.model.TranslationResponse;

@Component
public interface TranslationService {
    TranslationResponse translate(TranslationRequest request, String userIp);

     void saveTranslationRequest(String userIp, String originalText, String translatedText);
}
