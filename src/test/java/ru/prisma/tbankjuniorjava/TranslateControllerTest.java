package ru.prisma.tbankjuniorjava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import ru.prisma.tbankjuniorjava.model.TranslationRequest;
import ru.prisma.tbankjuniorjava.model.TranslationFromYandexApiResponse;
import ru.prisma.tbankjuniorjava.repository.TranslationRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@ContextConfiguration(initializers = PostgreSQLInitializer.class)
@Testcontainers
class TranslateControllerTest {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected WebTestClient webClient;

    @Autowired
    protected TranslationRepository translationRepository;

    private static final String PATH = "/api/v1/translate";

    @Test
    void getTranslateTest() {
        var request = TranslationRequest
                .builder()
                .text("Hello world!")
                .sourceLang("en")
                .targetLang("ru")
                .build();

        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH)
                        .build())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TranslationFromYandexApiResponse.class)
                .returnResult();

        assertThat(translationRepository.getAll().get(0).getOriginalText()).isEqualTo("Hello world!");
    }

    @Test
    void testNoTextForTranslation() {
        var request = TranslationRequest.builder()
                .sourceLang("en")
                .targetLang("ru")
                .build();

        webClient.post()
                .uri("/api/v1/translate")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Text cannot be null or empty.");
    }

    @Test
    void testNoSourceLang() {
        var request = TranslationRequest.builder()
                .text("Hello world!")
                .targetLang("ru")
                .build();

        webClient.post()
                .uri("/api/v1/translate")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Source language cannot be null or empty.");
    }

    @Test
    void testNoTargetLang() {
        var request = TranslationRequest.builder()
                .text("Hello world!")
                .sourceLang("en")
                .build();

        webClient.post()
                .uri("/api/v1/translate")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Target language cannot be null or empty.");
    }

}
