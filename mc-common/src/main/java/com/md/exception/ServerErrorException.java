package com.md.exception;

/** @author  */
public class ServerErrorException extends RuntimeException {
    public ServerErrorException(String coderMessage) {
        super(coderMessage);
    }
}