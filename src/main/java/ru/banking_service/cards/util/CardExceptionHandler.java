package ru.banking_service.cards.util;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CardExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(final ApplicationException exception,
                                                        final HttpServletRequest request) {
        String guid = UUID.randomUUID().toString();
        //log.error("Error GUID=%s; error message: %s", guid, exception.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                guid,
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getHttpStatus().value(),
                exception.getHttpStatus().name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String guid = UUID.randomUUID().toString();
        ApiErrorResponse response = ApiErrorResponse.builder()
                .guid(guid)
                .errorCode(exception.getFieldError().getCode())
                .message(exception.getMessage())
                .statusCode(exception.getStatusCode().value())
                .statusName(exception.getStatusCode().toString())
                .path(request.getContextPath())
                .method(request.getParameter("_method"))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(final Exception exception,
                                                   final HttpServletRequest request) {
        String guid = UUID.randomUUID().toString();
        //log.error(String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()));
        ApiErrorResponse response = new ApiErrorResponse(
                guid,
                exception.getCause().toString(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
