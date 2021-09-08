package edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/weather")
public class MyClient {

    static BufferedReader r = new BufferedReader( new InputStreamReader( System.in ));
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws UnknownHostException, IOException {

        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {

        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {

        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {

        //https://api.openweathermap.org/data/2.5/weather?q=London&APPID=8f7be5f45471c12644241ab5a22f7d32
        MyServer server = new MyServer();
        server.start();
        
        MyClient client = new MyClient();
        client.startConnection("127.0.0.1", 35000);
        System.out.print("Input: ");
        String input = r.readLine();

        if (!input.contains("http") && !"".equals(input)){
            input = "https://api.openweathermap.org/data/2.5/weather?q="+ input + "&APPID=8f7be5f45471c12644241ab5a22f7d32";
        }

        String response = client.sendMessage(input);
        
        while (!"".equals(input)) {
            
            System.out.println(response);

            System.out.print("Input: ");
            r = new BufferedReader( new InputStreamReader( System.in ));
            input = r.readLine();
            
            if (!input.contains("http") && !"".equals(input)){
                
                input = "https://api.openweathermap.org/data/2.5/weather?q="+ input + "&APPID=8f7be5f45471c12644241ab5a22f7d32";
            }

            response = client.sendMessage(input);
        }
    }
}


// Tomado de https://www.baeldung.com/a-guide-to-java-sockets