package com.example.signup.signupprocess;

import com.example.employee.Administrator;
import com.example.file.FileHandler;
import com.example.file.IdGenerator;
import com.example.signup.UserAlreadyExistException;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public class SignUpAdministrator extends SignUp{
    FileHandler fileHandler;
    IdGenerator employeeId;
    public SignUpAdministrator(IdGenerator employeeId) {
        this.employeeId = employeeId;
        signUp();
    }
    
    private void signUp(){
        fileHandler = FileHandler.getInstance();
        try {
            Buffer.getOut().println("Sign Up as Administrator:\n" +
                    "Administrator Name:");
            String adminName = Buffer.getIn().readLine();
            boolean taken = fileHandler.isTakenEmployee(adminName);
            Buffer.getOut().println(taken);
            if (taken) {
                Buffer.getOut().println("Administrator name is already taken. Invalid Input ✖");
                return;
            }
            String newAdminPassword = useGeneratedPassword();
            Buffer.getOut().println("Your password is: "+ newAdminPassword);
            Administrator newAdmin = new Administrator(adminName, newAdminPassword);
            int key = employeeId.generateKey();
            fileHandler.newEmployee(key, newAdmin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
