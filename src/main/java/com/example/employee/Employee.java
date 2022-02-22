package com.example.employee;

public class Employee {
    private String employeeName;
    private String password;
    private int ID;

    public Employee() {
    }

    public Employee(String employeeName, String password) {
        this.employeeName = employeeName;
        this.password = password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Employee" +
                "employeeName: " + employeeName +
                ", password: " + password +
                ", ID:: " + ID ;
    }
}
