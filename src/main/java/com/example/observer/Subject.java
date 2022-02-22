package com.example.observer;
import com.example.file.FileHandler;

import java.util.ArrayList;

public abstract class Subject extends Node {

    private ArrayList<Document> observers;

    public Subject(String nodeName, int nodeId) {
        super(nodeName, nodeId);
        observers = FileHandler.getDocumentsList();
    }

    public void notifyDocuments(String documentName){
        for (Document observer : observers) {
            if (observer.getDocumentName().equals(documentName))
                observer.update();
        }
    }
}


