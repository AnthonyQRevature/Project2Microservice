package com.example.exception;

/**
 * Server Failed to Authenticate the user corresponding to a 403 forbidden status code
 */
public class AuthenticationException extends Exception {
    public AuthenticationException() { super(); }
    public AuthenticationException(String msg) { super(msg); }
}
