package ru.prisma.tbankjuniorjava.client;

import org.springframework.stereotype.Component;

@Component
public interface TranslationClient {
    String translateWord(String word, String targetLang);
}
