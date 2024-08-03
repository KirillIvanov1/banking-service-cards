package ru.banking_service.cards.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
