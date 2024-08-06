package ru.prisma.tbankjuniorjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslationFromYandexApiResponse {

    @JsonProperty("translations")
    private List<Translation> translations;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Translation {

        @JsonProperty("text")
        private String text;
    }
}
