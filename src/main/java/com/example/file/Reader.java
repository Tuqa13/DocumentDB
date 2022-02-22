package com.example.file;

import com.example.employee.Employee;
import com.example.observer.Document;

import java.util.ArrayList;

public class Reader {
    private final Employee employee;
    FileHandler file;

    public Reader(Employee employee) {
        this.employee = employee;
        this.file = FileHandler.getInstance();
        if(!file.isTakenEmployee(employee.getEmployeeName()))
            file.newEmployee(employee.getID(), employee);
    }

    public ArrayList<Document> propertyIndex(String property) {
        return file.searchProperty(property);
    }

    public ArrayList<Document> multiPropertyIndex(String... property) {
        return file.searchMultiProperty(property);
    }

    public Document searchIndex(int idx) {
        return file.searchIndex(idx);
    }


}
