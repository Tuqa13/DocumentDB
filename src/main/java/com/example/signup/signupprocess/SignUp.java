package com.example.signup.signupprocess;

import com.example.file.*;
import com.example.login.PasswordException;
import com.example.password.*;
import com.example.socket.buffers.Buffer;

import java.io.*;

public abstract class SignUp {
    private IdGenerator employeeId;

    public SignUp() {

    }
    protected String suggestedPassword(String clientResponse) {
        try {
            if ("yes".equals(clientResponse)) {
                return PasswordGenerator.Generate();
            } else if ("no".equals(clientResponse)) {
                return generatePassword();
            } else {
                Buffer.getOut().println("✖ Invalid input");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PasswordGenerator.Generate();
    }

    private String generatePassword(){
        String employeePassword = null;
        try {
            Buffer.getOut().println("Password:");
            employeePassword = Buffer.getIn().readLine();
            if (!PasswordValidity.isValidPassword(employeePassword)) {
                Buffer.getOut().println("Your Password is not strong, Do you want to use strong Password instead? yes|no");
                String response = Buffer.getIn().readLine();
                if (response.toLowerCase().equals("yes")){
                    PasswordGenerator.Generate();
                }
                passwordError(employeePassword);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeePassword;
    }
    private void passwordError(String password) throws PasswordException {
        if (password.length() < 8 || password.length() > 15)
            throw new PasswordException(1);
        else if (password.contains(" "))
            throw new PasswordException(2);
        else if (password.contains("0123456789"))
            throw new PasswordException(3);
        else if (password.contains("@!#$%^?*+{}[]"))
            throw new PasswordException(4);
        else if(password.toLowerCase().equals(password))
            throw new PasswordException(5);
        else if(password.toUpperCase().equals(password))
            throw new PasswordException(6);
        else
            throw new PasswordException(7);
    }


    protected String useGeneratedPassword() throws IOException {
        Buffer.getOut().println("Do you want to use Suggested Password? yes|no");
        String suggestedPasswordResponse = Buffer.getIn().readLine();
        return suggestedPassword(suggestedPasswordResponse.toLowerCase());
    }
}

