package com.chatop.backend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionHandler extends RuntimeException{
    private HttpStatus httpStatus;

    public ExceptionHandler(String message) {
        super(message);
    }

    public ExceptionHandler(String message, HttpStatus httpStatus) {
      super(message);
      this.httpStatus = httpStatus;
    }

    public ExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionHandler(String message, Throwable cause, HttpStatus httpStatus) {
      super(message, cause);
      this.httpStatus = httpStatus;
    }
}
