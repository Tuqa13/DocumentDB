package com.example.file;

import com.example.cache.LRUCache;
import com.example.employee.Employee;
import com.example.observer.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler implements Operations {

    private static FileHandler fileHandler;
    private static ArrayList<Document> documentsList;
    private static ArrayList<Employee> employeeList;
    private static JSONObject documentsJson;
    private static JSONObject employeesJson;
    private static LRUCache cache;

    private FileHandler() {
        employeesJson = new JSONObject();
        documentsJson = new JSONObject();
        documentsList = new ArrayList<>();
        employeeList = new ArrayList<>();
        cache = LRUCache.getInstance();
    }

    public static FileHandler getInstance() {
        if (fileHandler == null) {
            fileHandler = new FileHandler();
        }
        return fileHandler;
    }

    private Employee loadEmployee(int id) {
        JSONObject employee= (JSONObject) employeesJson.get("0");
        Gson gson = new GsonBuilder().create();
        String json_string = gson.toJson(employee);
        Employee e = gson.fromJson(json_string, Employee.class);
//        Employee e = new Employee((String) employee.get("employeeName"), (String) employee.get("password"));
        return e;
    }

    private Document loadDocument(int id) {
        JSONObject document = (JSONObject) documentsJson.get(String.valueOf(id));
        Gson gson = new Gson();
        String json_String = gson.toJson(document);
        return gson.fromJson(json_String, Document.class);
    }

    public boolean isTakenDocument(String documentName) {
        for (Document value : documentsList) {
            Document document = loadDocument(value.getDocumentId());
            if (document.getDocumentName().equals(documentName))
                return true;
        }
        return false;
    }

    public boolean isTakenEmployee(String employeeName) {
        Employee e;
        for(int i = 0; i< employeeList.size(); i++){
            e = loadEmployee(employeeList.get(i).getID());
            if(e.getEmployeeName().equals(employeeName))
                return true;
        }
        return false;
    }

    public Employee returnEmployee(String employeeName) {
        if (isTakenEmployee(employeeName))
            for (Employee employee : employeeList) {
                Employee e = loadEmployee(employee.getID());
                if (e.getEmployeeName().equals(employeeName))
                    return e;
            }
        return null;
    }

    public static ArrayList<Document> getDocumentsList() {
        return documentsList;
    }

    public static ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }


    @Override
    public void addDocument(Document document) {
        synchronized (documentsList) {
            documentsList.add(document);
        }
        synchronized (cache){
            cache.put(document.getDocumentId(), document);
        }
        saveDocument(document.getDocumentId(), document);
    }

    @Override
    public void addJSONObject(String documentName) {
        Document myDocument = documentsList.stream().filter(c -> c.getDocumentName()
                .equals(documentName)).collect(Collectors.toList()).get(0);
            assert myDocument != null;
        synchronized (myDocument) {
            myDocument.update();
        }
    }

    public List<Employee> getDocuments() {
        ArrayList documentList = new ArrayList<>(cache.getElementsMap().values());
        ArrayList documents = new ArrayList();
        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i).getClass().getName().equals("Document")) {
                documents.add(documentList.get(i));
            }
        }
        return documents;
    }

    public void newEmployee(int id, Employee e) {
        saveEmployee(id, e);
    }

    private void saveEmployee(int id, Employee e) {
        JSONObject employee = new JSONObject();
        employee.put("employeeName", e.getEmployeeName());
        employee.put("ID", e.getID());
        employee.put("password", e.getPassword());
        employeeList.add(e);
        employeesJson.put(String.valueOf(id), employee);
        fileWriter(employeesJson, "Employee");

    }

    private void saveDocument(int id, Document document) {
        JSONObject document1 = new JSONObject();
        document1.put("Name", document.getDocumentName());
        document1.put("Id", document.getDocumentId());
        document1.put("Date", document.getCreationDate());
        documentsList.add(document);
        documentsJson.put(String.valueOf(id), document1);
        fileWriter(documentsJson, "Document");

    }

    public void fileWriter(JSONObject o, String table) {
        try {
            synchronized (this) {
                Path p = Paths.get("src/files/"+ table + "sFile.json");
                Files.write(p, Collections.singleton(o.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object loadFromFile(int id, String table) {
        if (table.equals("Document"))
            return loadDocument(id);
        else
            return loadEmployee(id);
    }

    public ArrayList<Document> searchProperty(String property) {
        ArrayList<Document> matchProperty = new ArrayList<>();
        for (Document document : documentsList) {
            if (document.propertyIndex(property))
                matchProperty.add(document);
        }
        return matchProperty;
    }

    public ArrayList<Document> searchMultiProperty(String... property) {
        ArrayList<Document> matchProperty = new ArrayList<>();
        boolean match;
        for (Document document : documentsList) {
            match = true;
            for (String i : property) {
                if (!document.propertyIndex(i)) {
                    match = false;
                    break;
                }}
            if (match) {
                matchProperty.add(document);
            }}
        return matchProperty;
    }

    public Document searchIndex(int idx) {
        if (idx > documentsList.size())
            return null;
        return documentsList.get(idx);
    }
}