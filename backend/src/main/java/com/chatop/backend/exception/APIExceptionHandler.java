package com.chatop.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ExceptionHandler.class})
    public ResponseEntity<Object> handleAPIRequestException(ExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                e,
                e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }


}
