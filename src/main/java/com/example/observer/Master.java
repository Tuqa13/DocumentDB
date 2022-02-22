package com.example.observer;

import com.example.employee.Employee;
import com.example.file.Writer;

import java.util.Date;

public class Master extends Subject {
    private static Date creationDate;
    private static Master masterNode;
    private static Employee employee;
    private static Writer write;

    private Master() {
        super("Master", 0);
        creationDate = new Date();
    }

    public static Master getInstance(Employee employee) {
        if (masterNode == null) {
            masterNode = new Master();
            Master.setEmployee(employee);
            write = new Writer(employee);
        }
        return masterNode;
    }

    public static void setEmployee(Employee employee) {
        Master.employee = employee;
    }

    public Document addNewDocument(int documentId) {
        return write.addNewDocument(documentId);
    }

    public void addJSONObject(String documentName, int jsonObjectId) {
        write.addJSONObject(documentName);
    }

    @Override
    public void notifyDocuments(String documentName){super.notifyDocuments(documentName);}


}
