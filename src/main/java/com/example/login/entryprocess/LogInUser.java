package com.example.login.entryprocess;

import com.example.employee.Employee;
import com.example.employee.User;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public class LogInUser extends LogIn {

    public LogInUser() {
        logIn();
    }

    private void logIn() {
        try {
            Buffer.getOut().println("User Name: "); //todo ✔
            String userName = Buffer.getIn().readLine(); //todo ✔
            User user = returnIfIsUser(userName);
            if(user == null)
                return;
            String password = matchPassword(user, 0);
            if (password == null)
                return;
            LogIn.setLoggedInEmployee(user);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Employee getLoggedInEmployee() {
        return LogIn.getLoggedInEmployee();
    }
}


