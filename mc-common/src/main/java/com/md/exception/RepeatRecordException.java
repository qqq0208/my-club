package com.md.exception;

public class RepeatRecordException extends RuntimeException {
    public RepeatRecordException(String coderMessage) {
        super(coderMessage);
    }
}