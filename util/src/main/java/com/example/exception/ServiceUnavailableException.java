package com.example.exception;

public class ServiceUnavailableException extends Exception {

    public ServiceUnavailableException(Throwable e) {
        super(e);
    }
    
}
