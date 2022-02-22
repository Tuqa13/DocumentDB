package com.example.login.loginprocess;

import com.example.file.IdGenerator;
import com.example.login.entryprocess.LogIn;
import com.example.observer.Document;
import com.example.observer.Master;
import com.example.password.WrongEntryException;
import com.example.socket.buffers.Buffer;
import com.example.socket.Server;

import java.io.IOException;


public class WriteProcess {
    Master master;

    public WriteProcess() {
        try {
            master = Master.getInstance(LogIn.getLoggedInEmployee());
            Buffer.getOut().println("Do you want to add new document or add new JSON Object? (1|2)");
            String choice = Buffer.getIn().readLine();
            choice(choice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void choice(String choice) throws IOException {
        if ("1".equals(choice)) {
            addDocument();
        } else if ("2".equals(choice)) {
            addJSONObject();
        } else {
            throw new WrongEntryException("You Entered wrong input.");
        }
    }

    private void addDocument() {
        Document newDocument = master.addNewDocument(Server.getDocumentId().generateKey());
        Server.getDocumentsJSONObjectsId().put(newDocument.getDocumentName(), new IdGenerator());
    }

    private void addJSONObject() throws IOException {
        Buffer.getOut().println("Document Name:");
        String documentName = Buffer.getIn().readLine();
        for (String i : Server.getDocumentsJSONObjectsId().keySet()) {
            if (i.equals(documentName)) {
                master.addJSONObject(documentName,
                        Server.getDocumentsJSONObjectsId().get(i).generateKey());
                break;
            }
        }
    }

}