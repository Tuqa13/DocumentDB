package com.example.login.entryprocess;

import com.example.employee.Employee;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public abstract class LogInFacade {

    public LogInFacade(Buffer buffer) {
    }

    public static Employee logIn() {
        try {
            Buffer.getOut().println("Do you want to logIn as User or Administrator?"); //todo ✔
            String employeeType = Buffer.getIn().readLine(); //todo ✔
            if ("administrator".equals(employeeType)) {
                LogInAdministrator logIn = new LogInAdministrator();
                return LogIn.getLoggedInEmployee();
            } else if ("user".equals(employeeType)) {
                new LogInUser();
                return LogIn.getLoggedInEmployee();
            } else {
                Buffer.getOut().println("Invalid Input"); //todo ✔
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
