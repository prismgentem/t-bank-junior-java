package ru.prisma.tbankjuniorjava.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Translation {
    private UUID id;
    private String userIp;
    private String originalText;
    private String translatedText;
    private LocalDateTime timestamp;
}
