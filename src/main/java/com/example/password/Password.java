package com.example.password;

public interface Password {

    static boolean isValidPassword(String password) {
        CharSequence NUMBERS = "0123456789";
        CharSequence SYMBOLS = "!#$%&()*+_";

        boolean hasNumbers = password.contains(NUMBERS);
        boolean hasSymbols = password.contains(SYMBOLS);

        return password.length() > 7 && hasNumbers && hasSymbols;
    }
}
