package ru.prisma.tbankjuniorjava.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prisma.tbankjuniorjava.model.TranslationRequest;
import ru.prisma.tbankjuniorjava.model.TranslationResponse;

@RequestMapping("/api/v1/translate")
public interface TranslationController {
    @PostMapping
    ResponseEntity<TranslationResponse> translateText(@RequestBody TranslationRequest request, HttpServletRequest httpServletRequest);
}
