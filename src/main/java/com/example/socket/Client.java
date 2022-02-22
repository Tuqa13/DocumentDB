package com.example.socket;

import com.example.employee.Administrator;
import com.example.employee.Employee;
import com.example.employee.User;
import com.example.file.FileHandler;
import com.example.file.Reader;
import com.example.login.PasswordException;
import com.example.login.loginprocess.WriteProcess;
import com.example.observer.Document;
import com.example.password.WrongEntryException;
import com.example.socket.buffers.Buffer;
import com.example.socket.buffers.ClientBuffer;
import org.json.JSONObject;
import org.springframework.cglib.beans.FixedKeySet;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Client {
    private static final int SERVICE_PORT = 1400;
    private Employee loggedInEmployee = null;
    private FileHandler file;

    public Client() {
        file = FileHandler.getInstance();
        getInstance();
    }

    public void getInstance() {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), SERVICE_PORT)) {

            ClientBuffer.createClientBuffer(socket);

            System.out.println(ClientBuffer.getIn().readLine());
            System.out.println(ClientBuffer.getIn().readLine());
            String response = ClientBuffer.getSc().nextLine().toLowerCase();  
            ClientBuffer.getOut().println(response);

            checkEmployeeResponse(response);

            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkEmployeeResponse(String response) throws IOException{
        if ("login".equals(response) || "log in".equals(response)) {
            logIn();
        } else if ("signup".equals(response) || "sign up".equals(response)) {
            ClientService service = new ClientService();
            service.signUp();
        }
        else{
            System.out.println(ClientBuffer.getIn().readLine());
        }
    }
    
    private void logIn()throws IOException{
        System.out.println(ClientBuffer.getIn().readLine());
        String response = ClientBuffer.getSc().nextLine().toLowerCase();
        ClientBuffer.getOut().println(response);
        if ("administrator".equals(response)) {
           administratorLogIn();

        } else if ("user".equals(response)) {
            userLogIn();
        }
        else
            System.out.println(ClientBuffer.getIn().readLine());
    }
    
    public void userLogIn() throws IOException{
        logInAsUserClient();
        System.out.println(ClientBuffer.getIn().readLine());
        search();
    }
    
    public void administratorLogIn() throws IOException{
        logInAsAdministratorClient();
        System.out.println(ClientBuffer.getIn().readLine());
        System.out.println(ClientBuffer.getIn().readLine());
        String adminResponse = ClientBuffer.getSc().nextLine().toLowerCase();
        ClientBuffer.getOut().println(adminResponse);

        if ("read".equals(adminResponse) || "1".equals(adminResponse)) {
            search();
        } else if ("write".equals(adminResponse) || "2".equals(adminResponse)) {
            write();
        } else {
            System.out.println(ClientBuffer.getIn().readLine());
        }

    }
    public void search() throws IOException {
        Reader read = new Reader(loggedInEmployee);
        ArrayList<Document> result;
        for (int i = 0; i < 5; i++)
            System.out.println(ClientBuffer.getIn().readLine());  
            String response = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(response);  
        if ("1".equals(response)) {
            System.out.println(ClientBuffer.getIn().readLine());
            String choice = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(choice);  
            result = read.propertyIndex(choice);
            printResult(result);

        } else if ("2".equals(response)) {
            System.out.println(ClientBuffer.getIn().readLine());  
            String choice = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(choice);  
            String[] properties = new String[10];  
            int i = 0;  
            while (!choice.equals("exit") && i < 10) {  
                properties[i] = choice;  
                i++;  
                choice = ClientBuffer.getSc().nextLine();  
                ClientBuffer.getOut().println(choice);  
            }
            result = read.multiPropertyIndex(properties);
            printResult(result);
        } else if ("3".equals(response)) {
            System.out.println(ClientBuffer.getIn().readLine());  
            String choice = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(choice);  
            Document indexResult = read.searchIndex(Integer.parseInt(choice));
            System.out.println(ClientBuffer.getIn().readLine());  
        } else {
            System.out.println(ClientBuffer.getIn().readLine());  
        }
    }

    public void logInAsAdministratorClient() {
        try {
            Administrator admin = null;
            System.out.println(ClientBuffer.getIn().readLine());  
            String adminName = ClientBuffer.getSc().nextLine();
            ClientBuffer.getOut().println(adminName);
            if (isRegistered(adminName))
                admin = (Administrator) file.returnEmployee(adminName);
            else {
                System.out.println("Admin name is not found, register first.");
                return;

            }
            assert admin != null;
            String password = matchPassword(admin, 0);
            if(password == null)
                return;
            loggedInEmployee = admin;  

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void logInAsUserClient() {
        try {
            User user;
            System.out.println(ClientBuffer.getIn().readLine());  
            String userName = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(userName);  
            if (isRegistered(userName))
                user = (User) file.returnEmployee(userName);
            else {
                System.out.println("User name is not registered.");
                return;
            }
            assert user != null;
            String password = matchPassword(user, 0);
            if(password == null)
                return;
            loggedInEmployee = user;  

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isRegistered(String name) throws IOException {
        if (!file.isTakenEmployee(name)) {
            System.out.println(ClientBuffer.getIn().readLine());
            return false;
        } else {
            return true;
        }
    }

    public String matchPassword(Employee e, int trial) {
        String password = null;
        try {
            System.out.println(ClientBuffer.getIn().readLine());  
            password = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(password);  
            if (!e.getPassword().equals(password)) {
                System.out.println(ClientBuffer.getIn().readLine());  
                trial++;
                if (trial == 5){
                    System.out.println(ClientBuffer.getIn().readLine());
                    return null;
                }
                matchPassword(e, trial);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return password;
    }

    public void printResult(ArrayList result) throws IOException {
        for (int i=0; i<result.size(); i++){
            documentToString();
        }
    }
    public void documentToString() throws IOException {
        for(int i=0; i<4; i++)
            System.out.println(ClientBuffer.getIn().readLine());  
    }
    
    public void write() throws IOException{
        System.out.println(ClientBuffer.getIn().readLine());  
        String choice = ClientBuffer.getSc().nextLine();  
        ClientBuffer.getOut().println(choice);  
        choice(choice);

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

    private void addDocument() throws IOException {
        System.out.println(ClientBuffer.getIn().readLine());  
        String choice = ClientBuffer.getSc().nextLine();  
        ClientBuffer.getOut().println(choice);  
        updateJSONObject(new JSONObject());

    }
    private JSONObject updateJSONObject(JSONObject obj){
        String key, value;
        try {
            System.out.println(ClientBuffer.getIn().readLine());  
            key = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(key);  
            System.out.println(ClientBuffer.getIn().readLine());  
            value = ClientBuffer.getSc().nextLine();  
            ClientBuffer.getOut().println(value);  
        } catch (IOException e) {
            System.out.println("Error");
        }
        updateJSONObject(obj);
        return obj;
    }

    private void addJSONObject() throws IOException {
        System.out.println(ClientBuffer.getIn().readLine());  
        String documentName = ClientBuffer.getSc().nextLine();  
        ClientBuffer.getOut().println(documentName);  
        }


    public static void main(String[] args) {
        new Client();
    }
}

