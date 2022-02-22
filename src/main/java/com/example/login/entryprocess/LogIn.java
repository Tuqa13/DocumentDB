package com.example.login.entryprocess;

import com.example.employee.Administrator;
import com.example.employee.Employee;
import com.example.employee.User;
import com.example.file.FileHandler;
import com.example.socket.buffers.Buffer;

import java.io.IOException;

public abstract class LogIn {
    private static Employee loggedInEmployee = null;
    private FileHandler file;


    public LogIn() {
        file = FileHandler.getInstance();
    }
    public static Employee getLoggedInEmployee(){
        return loggedInEmployee;
    }

    public boolean isRegistered(String name) {
        if (!file.isTakenEmployee(name)) {
            Buffer.getOut().println("Please register first.");
            return false;
        } else {
            return true;
        }
    }

    public String matchPassword(Employee e, int trial) {
        String password = null;
        try {
            Buffer.getOut().println("Password: "); //todo ✔
            password = Buffer.getIn().readLine(); //todo ✔
            if (!e.getPassword().equals(password)) {
                Buffer.getOut().println("Password is not correct, try again."); //todo ✔
                trial++;
                if (trial == 5) {
                    Buffer.getOut().println("Your trials expired, try again later.🚫");
                    return null;
                }
                matchPassword(e, trial);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return password;
    }

    public String getEmployeeType() {
        assert loggedInEmployee != null;
        return loggedInEmployee.getClass().getName();
    }

    public static void setLoggedInEmployee(Employee loggedInEmployee) {
        LogIn.loggedInEmployee = loggedInEmployee;
    }

    public Employee checkRegister(String employeeName){
        if (isRegistered(employeeName))
            return file.returnEmployee(employeeName);
        else
            return null;
    }
    public User returnIfIsUser(String userName){
        if(isRegistered(userName)){
            Employee e = file.returnEmployee(userName);
            if(e.getClass().getName().replace("com.example.employee.", "").toLowerCase().equals("user"))
                return (User)e;
        }
        return null;
    }
    public Administrator returnIfIsAdministrator(String adminName){
        if(isRegistered(adminName)){
            Employee e = file.returnEmployee(adminName);
            if(e.getClass().getName().replace("com.example.employee.", "").toLowerCase().equals("administrator"))
                return (Administrator) e;
        }
        return null;
    }
}
