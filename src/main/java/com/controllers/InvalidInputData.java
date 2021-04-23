package com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class InvalidInputData extends RuntimeException{



    public InvalidInputData(String message) {
        super(message);

    }
}
