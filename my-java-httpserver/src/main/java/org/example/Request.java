package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Request implements Runnable {

    private Socket clientSocket;


    public Request(Socket socket) {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            String header = line;
            while (!line.isEmpty()) {
                line = reader.readLine();
                header +=line+"\n";
            }
            String output = "HTTP/1.1 200 OK\r\n\r\nResponse OK\n\n"+header;
            clientSocket.getOutputStream().write(output.getBytes("UTF-8"));
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
