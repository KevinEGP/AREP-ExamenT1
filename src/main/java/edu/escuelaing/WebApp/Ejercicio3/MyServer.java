package edu.escuelaing.WebApp.Ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        int number = Integer.parseInt(in.readLine());
        
        out.println(number*number);
    }

    public void stop() throws IOException {

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException {
        MyServer server = new MyServer();
        server.start(6666);
    }
    
}


// Tomado de https://www.baeldung.com/a-guide-to-java-sockets