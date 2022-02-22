package com.example.login.entryprocess;

import com.example.employee.Administrator;
import com.example.employee.Employee;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public class LogInAdministrator extends LogIn{

    public LogInAdministrator() {
        this.logIn();

    }
    private void logIn(){
        try {
            Buffer.getOut().println("Administrator Name: "); //todo ✔
            String adminName = Buffer.getIn().readLine(); //todo ✔
            Employee admin = returnIfIsAdministrator(adminName);
            if(admin == null)
                return;
            String password = matchPassword(admin, 0); //todo ✔
            if(password == null)
                return;
            LogIn.setLoggedInEmployee(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee getLoggedInEmployee() {
        return LogIn.getLoggedInEmployee();
    }


}
