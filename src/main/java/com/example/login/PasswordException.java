package com.example.login;

import java.io.IOException;

/**
 * Throw exception when any password error occurs.
 */
public class PasswordException extends IOException {
    int passwordConditionViolated = 0;

    public PasswordException(int conditionViolated)
    {
        super("Invalid Password: ");
        passwordConditionViolated = conditionViolated;
        System.out.println(printMessage());
    }

    public String printMessage()
    {
        if (passwordConditionViolated == 1) {
            return ("Password length should be between 8 to 15 characters");
        } else if (passwordConditionViolated == 2) {
            return ("Password should not contain any space");
        } else if (passwordConditionViolated == 3) {
            return ("Password should contain at least one digit(0-9)");
        } else if (passwordConditionViolated == 4) {
            return ("Password should contain at least one special character");
        } else if (passwordConditionViolated == 5) {
            return ("Password should contain at least one uppercase letter(A-Z)");
        } else if (passwordConditionViolated == 6) {
            return ("Password should contain at least one lowercase letter(a-z)");
        } else if (passwordConditionViolated == 7) {
            return ("Invalid password, try again later");
        }

        return ("");
    }
}
