package com.example.socket;

import com.example.employee.Employee;
import com.example.file.*;
import com.example.employee.User;
import com.example.login.loginprocess.SearchProcess;
import com.example.login.loginprocess.WriteProcess;
import com.example.login.entryprocess.LogInFacade;
import com.example.password.WrongEntryException;
import com.example.signup.signupprocess.SignUpFacade;
import com.example.socket.buffers.Buffer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.util.*;

@RestController
@RequestMapping("/rest/docker/main")
public class Server {
    public static final int SERVICE_PORT = 1400;
    private static IdGenerator documentId;
    private static IdGenerator userId;
    private static IdGenerator adminId; // TODO check if you can do this in a different way.
    private static HashMap<String, IdGenerator> documentsJSONObjectsId;

    public static IdGenerator getDocumentId() {
        return documentId;
    }

    public static HashMap<String, IdGenerator> getDocumentsJSONObjectsId() {
        return documentsJSONObjectsId;
    }

    @GetMapping
    public static void main(String[] args) {
        documentId = new IdGenerator();
        userId = new IdGenerator();
        adminId = new IdGenerator();
        documentsJSONObjectsId = new HashMap<>();
        startSocket();

    }

    private static void startSocket(){
        ServerSocket server = null;
        try {
            server = new ServerSocket(SERVICE_PORT);
            server.setReuseAddress(true);
            System.out.println("Server Started.");
            receiveSocket(server);
        } catch (BindException be) {
            System.err.println("Service already running on port " + SERVICE_PORT);
        } catch (IOException ioe) {
            System.err.println("I/O error - " + ioe);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void receiveSocket(ServerSocket server) throws IOException {
        Socket client = server.accept();
        System.out.println("New client connected" + client.getInetAddress().getHostAddress());
        ClientHandler clientSocket = new ClientHandler(client);
        new Thread(clientSocket).start();
        receiveSocket(server);
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private Employee loggedInEmployee;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {

                Buffer.getBuffer(clientSocket);
                System.out.println("Connection Accepted.");
                String employeeResponse = employeeResponse();
                checkEmployeeResponse(employeeResponse.toLowerCase());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (Buffer.getOut() != null) {
                        Buffer.getOut().close();
                    }
                    if (Buffer.getIn() != null) {
                        Buffer.getIn().close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String employeeResponse() throws IOException{
            Buffer.getOut().println("Hello, Welcome to Atypon DB" + //todo ✔
                    "\nDo you want to sign up or log in?"); //todo ✔

            String response = Buffer.getIn().readLine(); //todo ✔
            return response;
        }
        private void checkEmployeeResponse(String employeeResponse) throws IOException {
            if ("login".equals(employeeResponse) || "log in".equals(employeeResponse)) {
                logInResponse();
            } else if ("signup".equals(employeeResponse) || "sign up".equals(employeeResponse)) {
                SignUpFacade facade = new SignUpFacade(adminId, userId);
                facade.signUp();
            } else {
                Buffer.getOut().println("✖ Invalid input");
                throw new WrongEntryException("You entered wrong input.");
            }

        }
        private void logInResponse() throws IOException {
            loggedInEmployee = LogInFacade.logIn(); //todo ✔

            if (loggedInEmployee instanceof User) {
                Buffer.getOut().println("Welcome " + loggedInEmployee.getEmployeeName()); //todo✔
                new SearchProcess(); //todo✔
            } else {
                administratorLogInResponse();
            }
        }
        private void administratorLogInResponse() throws IOException{
                Buffer.getOut().println("Welcome " + loggedInEmployee.getEmployeeName());
                Buffer.getOut().println("Do you want to read from file or to write to file? (read|write)(1|2)");
                String response = Buffer.getIn().readLine();
                if ("read".equals(response) || "1".equals(response)) {
                    new SearchProcess();
                } else if ("write".equals(response) || "2".equals(response)) {
                    new WriteProcess();
                } else {
                    Buffer.getOut().println("✖ Invalid input");
                }}
        }
    }
