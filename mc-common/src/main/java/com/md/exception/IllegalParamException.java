package com.md.exception;

public class IllegalParamException extends RuntimeException {
    public IllegalParamException(String coderMessage) {
        super(coderMessage);
    }
}