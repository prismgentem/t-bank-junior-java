package ru.prisma.tbankjuniorjava.exception;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String code;
    private String message;
    private Map<String, String> info;
}
