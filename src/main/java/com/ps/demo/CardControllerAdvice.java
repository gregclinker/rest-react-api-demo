package com.ps.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CardControllerAdvice {

    @ResponseBody
    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Error cardNotFoundException(CardNotFoundException ex) {
        return new Error("CardNotFoundException", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidCardNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Error invalidCardNumberException(InvalidCardNumberException ex) {
        return new Error("InvalidCardNumberException", ex.getMessage());
    }
}