package com.example.observer;

import com.example.socket.buffers.Buffer;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Document extends Node implements Observer {
    private final Date creationDate;
    private final HashMap<Integer, JSONObject> documentData = new HashMap<>();

    public Document(String documentName, int documentId) {
        super(documentName, documentId);
        creationDate = new Date();
    }


    public boolean isEmpty() {
        return documentData.size() == 0;
    }

    public JSONObject createJSONObject() {
        return addToJSONObject(new JSONObject());
    }

    private JSONObject addToJSONObject(JSONObject obj){
        String key, value;
        try {
            System.out.println("Key: Enter exit if you end.");
            key = Buffer.getIn().readLine();
            if (key.equals("exit")) {
                return obj;
            }
            System.out.println("Value");
            value = Buffer.getIn().readLine();
            obj.append(key, value);
        } catch (IOException e) {
            System.out.println("Error");
        }
        addToJSONObject(obj);
        return obj;
    }

    @Override
    public void update() {
        this.documentData.put(documentData.size(), createJSONObject());
    }

    public boolean propertyIndex(String property) {
        for (Map.Entry<Integer, JSONObject> entry : documentData.entrySet()) {
            JSONObject list = entry.getValue();
            if (list.has(property)) {
                return true;
            }
        }
        return false;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Document:" + this.getDocumentName() +
                "\tID:" + this.getDocumentId() +
                "\tCreationDate:" + creationDate +
                "\tDocumentData:" + documentData.toString().replace("{", "")
                .replace("[", "").replace("]", "")
                .replace("}", "").replace("=", " = ")
                .replace("\"", "").replace(",", ", ");

    }

}
