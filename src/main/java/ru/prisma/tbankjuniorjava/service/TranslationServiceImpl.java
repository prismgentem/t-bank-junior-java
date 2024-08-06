package ru.prisma.tbankjuniorjava.service;

import lombok.RequiredArgsConstructor;
import ru.prisma.tbankjuniorjava.client.TranslationClient;
import ru.prisma.tbankjuniorjava.entity.Translation;
import ru.prisma.tbankjuniorjava.model.TranslationRequest;
import ru.prisma.tbankjuniorjava.model.TranslationResponse;
import ru.prisma.tbankjuniorjava.repository.TranslationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private static final int MAX_THREADS = 10;
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    private final TranslationRepository translationRepository;
    private final TranslationClient translationClient;
    private final ValidationService validationService;

    @Override
    public TranslationResponse translate(TranslationRequest request, String userIp) {
        validationService.validateTranslationRequest(request);
        var words = request.getText().split(" ");
        var futures = new ArrayList<CompletableFuture<String>>();

        for (String word : words) {
            futures.add(CompletableFuture.supplyAsync(() -> translationClient.translateWord(word, request.getTargetLang()), executor));
        }

        List<String> translatedWords = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        var translatedText = String.join(" ", translatedWords);
        saveTranslationRequest(userIp, request.getText(), translatedText);
        return TranslationResponse.builder().translatedText(translatedText).build();
    }

    @Override
    public void saveTranslationRequest(String userIp, String originalText, String translatedText) {
        var request = Translation
                .builder()
                .userIp(userIp)
                .originalText(originalText)
                .translatedText(translatedText)
                .timestamp(LocalDateTime.now())
                .build();
        translationRepository.save(request);
    }

}
