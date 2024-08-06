package ru.prisma.tbankjuniorjava.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.prisma.tbankjuniorjava.exception.ExceptionService;
import ru.prisma.tbankjuniorjava.model.TranslationRequestForYandexApi;
import ru.prisma.tbankjuniorjava.model.TranslationFromYandexApiResponse;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TranslationClientImpl implements TranslationClient {

    @Value("${app.translation.api.url}")
    private String apiUrl;

    @Value("${app.translation.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public String translateWord(String word, String targetLang) {
        var requestBody = TranslationRequestForYandexApi.builder()
                .texts(Collections.singletonList(word))
                .targetLanguageCode(targetLang)
                .build();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Api-Key " + apiKey);

        var entity = new HttpEntity<>(requestBody, headers);

        try {
            var response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, TranslationFromYandexApiResponse.class);
            return response.getBody().getTranslations().get(0).getText();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new ExceptionService(extractMessageFromYandexException(ex.getResponseBodyAsString()), ex.getStatusCode().value(), ex.getStatusText());
        }

    }

    private String extractMessageFromYandexException(String messageResponse){

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String message = messageResponse.replaceAll("<EOL>", "");
            JsonNode rootNode = objectMapper.readTree(message);


            return rootNode.path("message").asText();
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage(), 500, e.getMessage());
        }
    }
}
