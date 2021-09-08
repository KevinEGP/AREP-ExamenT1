package edu.eci.arep;

import java.io.*;
import java.net.*;

public class MyServer extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";

    BufferedReader r = new BufferedReader( new InputStreamReader( System.in ));
    

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

    public void begin() throws IOException {

        int port = getPort();
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String inputLine;
        String responseLine;
        
        while (!(inputLine = in.readLine()).equals("")) {
            
            URL myUrl = new URL(inputLine);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()));

            while ((responseLine = reader.readLine()) != null) {
                response += "" + responseLine;
            }

            out.println(response);
            
            response = "";
        }

        in.close();
        out.close();

        clientSocket.close();
        serverSocket.close();
    }


    @Override
    public void run() {

        MyServer server = new MyServer();
        try {
            server.begin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }    
}


// Tomado de https://www.baeldung.com/a-guide-to-java-sockets