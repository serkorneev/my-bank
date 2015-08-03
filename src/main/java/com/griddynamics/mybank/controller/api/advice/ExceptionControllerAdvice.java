package com.griddynamics.mybank.controller.api.advice;

import com.griddynamics.mybank.exception.CardIsLockedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

/**
 * @author Sergey Korneev
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException() {}

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CardIsLockedException.class)
    public void handleCardLockedException() {}
}
