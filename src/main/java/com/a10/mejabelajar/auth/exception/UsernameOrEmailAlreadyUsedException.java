package com.a10.mejabelajar.auth.exception;

public class UsernameOrEmailAlreadyUsedException extends RuntimeException {
    public UsernameOrEmailAlreadyUsedException(String e) {
        super(e);
    }
}
