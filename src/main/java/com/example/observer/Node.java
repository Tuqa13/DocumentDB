package com.example.observer;

public abstract class Node {
    private String documentName;
    private int documentId;

    public Node(String documentName, int documentId) {
        this.documentName = documentName;
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public int getDocumentId() {
        return documentId;
    }
}
