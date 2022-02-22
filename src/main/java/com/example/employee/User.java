package com.example.employee;

public class User extends Employee {

    public User(String userName, String password) {
        super(userName, password);
    }

    public String getEmployeeName() {
        return super.getEmployeeName();
    }

    public void setEmployeeName(String employeeName) {
        super.setEmployeeName(employeeName);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public int getID() {
        return super.getID();
    }

    public void setID(int ID) {
        super.setID(ID);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
