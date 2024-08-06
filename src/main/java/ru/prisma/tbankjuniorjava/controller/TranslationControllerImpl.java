package ru.prisma.tbankjuniorjava.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.prisma.tbankjuniorjava.model.TranslationRequest;
import ru.prisma.tbankjuniorjava.model.TranslationResponse;
import ru.prisma.tbankjuniorjava.service.TranslationServiceImpl;
import ru.prisma.tbankjuniorjava.util.IpUtil;

@Controller
@RequiredArgsConstructor
public class TranslationControllerImpl implements TranslationController {
    private final TranslationServiceImpl translationService;

    @Override
    public ResponseEntity<TranslationResponse> translateText(@RequestBody TranslationRequest request, HttpServletRequest httpServletRequest) {
        var userIp = IpUtil.getClientIp(httpServletRequest);

        return ResponseEntity.ok(translationService.translate(request, userIp));
    }
}
