package com.gearfound.gearfoundauthorizationservice.web;

import com.gearfound.gearfoundauthorizationservice.users.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    public void handleConflict() {
    }
}

