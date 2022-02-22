package com.example.signup.signupprocess;

import com.example.employee.User;
import com.example.file.FileHandler;
import com.example.file.IdGenerator;
import com.example.signup.UserAlreadyExistException;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public class SignUpUser extends SignUp{
    FileHandler fileHandler;
    IdGenerator employeeId;
    public SignUpUser(IdGenerator employeeId) {
        this.employeeId = employeeId;
        signUp();
    }


    private void signUp() {
        fileHandler = FileHandler.getInstance();
        try {
            Buffer.getOut().println("Sign Up as User:\n" +
                    "User Name:");
            String userName = Buffer.getIn().readLine();
//            boolean taken = fileHandler.isTakenEmployee(userName);
//            Buffer.getOut().println(taken);
//            if (taken) {
//                Buffer.getOut().println("User name is already taken. Invalid Input ✖");
//                return;
//            }
            String newUserPassword = useGeneratedPassword();
            Buffer.getOut().println("Your password is: "+ newUserPassword);
            User newUser = new User(userName, newUserPassword);
            fileHandler.newEmployee(employeeId.generateKey(), newUser);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

}
