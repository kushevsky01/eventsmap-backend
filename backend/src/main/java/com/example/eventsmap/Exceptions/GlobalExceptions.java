package com.example.eventsmap.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException e) {
        List<Path> name = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getPropertyPath).toList();

        List<String> errorText = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage).toList();

        List<ErrorResponse> errorsResponses = new ArrayList<>();
        ErrorResponse error = new ErrorResponse(name.get(0).toString(), errorText.get(0));
        errorsResponses.add(error);

        return new ResponseEntity<>(errorsResponses, HttpStatus.BAD_REQUEST);
    }
}
