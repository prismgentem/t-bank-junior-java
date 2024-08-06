package ru.prisma.tbankjuniorjava.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TranslationRequestForYandexApi {
    private List<String> texts;
    private String targetLanguageCode;
}
