package org.imures.cashregister.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundException(EntityNotFoundException ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorMessage(OffsetDateTime.now(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {NullValueException.class})
    public ResponseEntity<ErrorMessage> handleNullValueException(NullValueException ex) {
        log.error("NullValueException: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorMessage(OffsetDateTime.now(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<ErrorMessage> handleIOException(IOException ex) {
        log.error("IOException: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorMessage(OffsetDateTime.now(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = { NoResourceFoundException.class })
    public ResponseEntity<ErrorMessage> handleException(
            final NoResourceFoundException ex
    ) {
        log.info("NoResourceFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorMessage(OffsetDateTime.now(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = { AuthorizationDeniedException.class })
    public ResponseEntity<ErrorMessage> handleException(
            final AuthorizationDeniedException ex
    ) {
        log.warn("AuthorizationDeniedException: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorMessage(OffsetDateTime.now(), ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleAllExceptions(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorMessage(OffsetDateTime.now(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}

