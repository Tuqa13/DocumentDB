package com.example.socket.buffers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Buffer {
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static Buffer buffer = null;

    public Buffer() {
    }
    public static Buffer getBuffer(Socket clientSocket){
        createBuffer(clientSocket);
        return buffer;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static BufferedReader getIn() {
        return in;
    }


    private static void createBuffer(Socket clientSocket){
        buffer = new Buffer();
        declarePara(clientSocket);
    }


    private static void declarePara(Socket clientSocket){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
