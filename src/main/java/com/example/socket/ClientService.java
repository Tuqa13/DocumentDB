package com.example.socket;

import com.example.file.FileHandler;
import com.example.file.Operations;
import com.example.password.PasswordGenerator;
import com.example.password.PasswordValidity;
import com.example.socket.buffers.Buffer;
import com.example.socket.buffers.ClientBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientService {
    PrintWriter out = null;
    BufferedReader in = null;
    Scanner sc = null;
    Operations fileHandler;

    ClientService() {
        this.fileHandler = FileHandler.getInstance();
    }

    public void signUp() {
        try {
            System.out.println(ClientBuffer.getIn().readLine());
            String employeeType = ClientBuffer.getSc().nextLine().toLowerCase();
            ClientBuffer.getOut().println(employeeType);
            if ("administrator".equals(employeeType)) {
                signUpAsAdministrator();
            } else if ("user".equals(employeeType)) {
                signUpAsUser();
            } else {
                System.out.println(ClientBuffer.getIn().readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String suggestedPassword(String clientResponse) {
            try {
        if ("yes".equals(clientResponse)) {
            return PasswordGenerator.Generate();
        } else if ("no".equals(clientResponse)) {
                System.out.println(ClientBuffer.getIn().readLine());
                String adminPassword =  ClientBuffer.getSc().nextLine();
                ClientBuffer.getOut().println(adminPassword);
                if (!PasswordValidity.isValidPassword(adminPassword)) {
                    System.out.println(ClientBuffer.getIn().readLine());
                    String response =  ClientBuffer.getSc().nextLine();
                    ClientBuffer.getOut().println(response);
                    suggestedPassword(response.toLowerCase());
                }
                return adminPassword;
        } else {
            System.out.println(ClientBuffer.getIn().readLine());
        }

            } catch (IOException e) {
                e.printStackTrace();
            }
        return PasswordGenerator.Generate();
    }

    private void signUpAsAdministrator() {
        try {
            System.out.println(ClientBuffer.getIn().readLine());
            System.out.println(ClientBuffer.getIn().readLine());
            String adminName =  ClientBuffer.getSc().nextLine();
            ClientBuffer.getOut().println(adminName);
            String taken = ClientBuffer.getIn().readLine();
            System.out.println(taken);
            if (taken.equals("true")) {
                System.out.println(ClientBuffer.getIn().readLine());
                return;
            }
            System.out.println(ClientBuffer.getIn().readLine());
            String suggestedPasswordResponse =  ClientBuffer.getSc().nextLine();
            ClientBuffer.getOut().println(suggestedPasswordResponse);
            if(suggestedPasswordResponse.toLowerCase().equals("no")){
                generatePassword();
            }
            if(suggestedPasswordResponse.toLowerCase().equals("yes")){
                System.out.println(ClientBuffer.getIn().readLine());
            }
            suggestedPassword(suggestedPasswordResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void signUpAsUser() {
        try {
            System.out.println(ClientBuffer.getIn().readLine());
            System.out.println(ClientBuffer.getIn().readLine());
            String userName =  ClientBuffer.getSc().nextLine();
            ClientBuffer.getOut().println(userName);
//            String taken = ClientBuffer.getIn().readLine();
//            if (taken.equals("true")) {
//                System.out.println(ClientBuffer.getIn().readLine());
//                return;
//            }
            System.out.println(ClientBuffer.getIn().readLine());
            // todo "Do you want to use Suggested Password? yes|no"
            String suggestedPasswordResponse =  ClientBuffer.getSc().nextLine();
            ClientBuffer.getOut().println(suggestedPasswordResponse);
            if(suggestedPasswordResponse.toLowerCase().equals("no")){
                generatePassword();
            }
            if(suggestedPasswordResponse.toLowerCase().equals("yes")){
                System.out.println(ClientBuffer.getIn().readLine());
            }
           suggestedPassword(suggestedPasswordResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void generatePassword() throws IOException {
        System.out.println(ClientBuffer.getIn().readLine());
        String employeePassword =  ClientBuffer.getSc().nextLine();
        ClientBuffer.getOut().println(employeePassword);
        if (!PasswordValidity.isValidPassword(employeePassword)) {
            System.out.println(ClientBuffer.getIn().readLine());
            String response =  ClientBuffer.getSc().nextLine();
            ClientBuffer.getOut().println(response);
        }
    }

}
