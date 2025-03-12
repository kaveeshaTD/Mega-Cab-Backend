package com.cybertronix.vehiclebooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException{
//comment for testing
    // public InternalServerErrorException(String message){
    //     super(message);
    // }
}
