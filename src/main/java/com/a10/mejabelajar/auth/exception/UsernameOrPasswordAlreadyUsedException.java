package com.a10.mejabelajar.auth.exception;

public class UsernameOrPasswordAlreadyUsedException extends RuntimeException{
    public UsernameOrPasswordAlreadyUsedException(String e){
        super(e);
    }
}
