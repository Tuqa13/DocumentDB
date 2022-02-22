package com.example.signup.signupprocess;

import com.example.file.IdGenerator;
import com.example.password.WrongEntryException;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public class SignUpFacade {
    IdGenerator adminId;
    IdGenerator userId;

    public SignUpFacade(IdGenerator adminId, IdGenerator userId) {
        this.adminId = adminId;
        this.userId = userId;
    }

    public void signUp(){
        try {
            Buffer.getOut().println("Do you want to sign up as User or Administrator?");
            String employeeType = Buffer.getIn().readLine();
            if ("administrator".equals(employeeType)) {
                new SignUpAdministrator(adminId);
            } else if ("user".equals(employeeType)) {
                new SignUpUser(userId);
            } else {
                Buffer.getOut().println("✖ Invalid input");
                throw new WrongEntryException("You entered wrong input.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
