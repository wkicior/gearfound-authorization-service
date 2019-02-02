package com.gearfound.authservice.web;

import com.gearfound.authservice.users.UserAlreadyExistsException;
import com.gearfound.authservice.users.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    public void handleConflict() {
        //
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public void handleNotFound() {
        //
    }
}

