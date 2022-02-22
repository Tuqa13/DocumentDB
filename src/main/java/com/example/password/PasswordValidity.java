package com.example.password;

public class PasswordValidity implements Password{
    public PasswordValidity() {}

    public static boolean isValidPassword(String password) {
        return Password.isValidPassword(password);
    }


}
