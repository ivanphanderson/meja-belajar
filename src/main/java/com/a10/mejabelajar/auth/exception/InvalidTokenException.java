package com.a10.mejabelajar.auth.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String e){
        super(e);
    }
}
