package ru.prisma.tbankjuniorjava.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExceptionService extends RuntimeException{
    private final Integer status;
    private final String code;

    public ExceptionService(String message, Integer status, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
