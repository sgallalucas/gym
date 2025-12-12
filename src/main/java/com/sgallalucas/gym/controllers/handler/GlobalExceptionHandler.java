package com.sgallalucas.gym.controllers.handler;

import com.sgallalucas.gym.controllers.DTOs.errors.ErrorResponseDetails;
import com.sgallalucas.gym.controllers.DTOs.errors.FieldErrorDetails;
import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.exceptions.NotAllowedOperationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErrorResponseDetails handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldError = ex.getFieldErrors();
        List<FieldErrorDetails> details = fieldError.stream().map(
                (fe) -> new FieldErrorDetails(fe.getField(), fe.getDefaultMessage())).toList();

        return new ErrorResponseDetails(HttpStatus.UNPROCESSABLE_CONTENT.value(), "Validation error", Instant.now(), details);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDetails handleDuplicateRecordException(DuplicateRecordException ex) {
        return new ErrorResponseDetails(HttpStatus.CONFLICT.value(), ex.getMessage(), Instant.now(), List.of());
    }

    @ExceptionHandler(NotAllowedOperationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponseDetails handleNotAllowedOperationException(NotAllowedOperationException ex) {
        return new ErrorResponseDetails(HttpStatus.OK.value(), ex.getMessage(), Instant.now(), List.of());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDetails handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorResponseDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now(), List.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDetails handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponseDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now(), List.of());
    }
}

