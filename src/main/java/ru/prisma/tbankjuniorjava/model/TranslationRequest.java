package ru.prisma.tbankjuniorjava.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationRequest {
    private String text;
    private String sourceLang;
    private String targetLang;
}
