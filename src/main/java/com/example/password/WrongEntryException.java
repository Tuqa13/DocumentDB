package com.example.password;


public class WrongEntryException extends IllegalArgumentException {
    public WrongEntryException(String msg) {
        super(msg);
    }
}
