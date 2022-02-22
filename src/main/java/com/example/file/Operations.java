package com.example.file;

import com.example.employee.Employee;
import com.example.observer.Document;

import java.util.ArrayList;

public interface Operations {

    void addDocument(Document document);

    void addJSONObject(String documentName);

    void newEmployee(int id, Employee e);

    ArrayList<Document> searchProperty(String property);

    public ArrayList<Document> searchMultiProperty(String... property);

    public Document searchIndex(int idx);
}