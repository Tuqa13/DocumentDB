package com.example.socket.buffers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientBuffer {
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static Scanner sc = null;


    public static void createClientBuffer(Socket clientSocket){
        declarePara(clientSocket);
    }

    private static void declarePara(Socket clientSocket){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sc = new Scanner(System.in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static Scanner getSc() {
        return sc;
    }
}
