package com.example.file;

import com.example.employee.Employee;
import com.example.observer.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Writer {
    Operations file;
    Employee employee;

    public Writer(Employee employee) {
        this.employee = employee;
        this.file = FileHandler.getInstance();
        file.newEmployee(employee.getID(), employee);
    }

    public Document addNewDocument(int documentId) {
        Document newDocument = newDocument(documentId);
        file.addDocument(newDocument);
        return newDocument;
    }

    public void addJSONObject(String documentName) {
        file.addJSONObject(documentName);
    }

    public Document newDocument(int documentId) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Document newDocument = null;
        System.out.println("Document name:");
        try {
            String documentName = reader.readLine();
            newDocument = new Document(documentName, documentId);
            newDocument.createJSONObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newDocument;
    }

}

