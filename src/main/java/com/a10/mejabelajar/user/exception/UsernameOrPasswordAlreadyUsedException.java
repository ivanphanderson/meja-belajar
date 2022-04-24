package com.a10.mejabelajar.user.exception;

public class UsernameOrPasswordAlreadyUsedException extends RuntimeException{
    public UsernameOrPasswordAlreadyUsedException(String e){
        super(e);
    }
}
