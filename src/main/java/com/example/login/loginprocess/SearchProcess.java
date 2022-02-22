package com.example.login.loginprocess;

import com.example.file.Reader;
import com.example.login.entryprocess.LogIn;
import com.example.observer.Document;
import com.example.socket.buffers.Buffer;

import java.io.IOException;
import java.util.ArrayList;

public class SearchProcess {

    public SearchProcess() {
        try {
            Reader read;
            ArrayList<Document> result;
            read = new Reader(LogIn.getLoggedInEmployee());
            Buffer.getOut().println("Do you want to: \n" + //todo✔
                    "1. Search about one property. \n" + //todo✔
                    "2. Search about multi-properties. \n" + //todo✔
                    "3. Search about specific Index. \n" + //todo✔
                    "(1|2|3)"); //todo✔
            String response = Buffer.getIn().readLine();
            String choice;
            if ("1".equals(response)) {
                Buffer.getOut().println("Property:");
                choice = Buffer.getIn().readLine();
                result = read.propertyIndex(choice);
                printResult(result);
            } else if ("2".equals(response)) {
                Buffer.getOut().println("Enter Properties: (Up to ten) (enter exit when done)");
                choice = Buffer.getIn().readLine();
                String[] properties = new String[10];
                int i = 0;
                while (!choice.equals("exit") && i < 10) {
                    properties[i] = choice;
                    i++;
                    choice = Buffer.getIn().readLine();
                }
                result = read.multiPropertyIndex(properties);
                printResult(result);
            } else if ("3".equals(response)) {
                Buffer.getOut().println("Enter Index: ");
                choice = Buffer.getIn().readLine();
                Document indexResult = read.searchIndex(Integer.parseInt(choice));
                Buffer.getOut().println(indexResult.toString());
            } else {
                Buffer.getOut().println("Wrong Input.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printResult(ArrayList<Document> result) {
        for (Document document : result) {
            Buffer.getOut().println(document.toString());
        }
    }
}
