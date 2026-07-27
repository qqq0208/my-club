package com.md.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String coderMessage) {
        super(coderMessage);
    }
}