package com.myjava.stresstester.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Request implements Runnable {

    private final Socket clientSocket;


    public Request(Socket socket) {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try {

            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();

            StringBuilder header = new StringBuilder(line);
            while (!line.isEmpty()) {
                line = reader.readLine();
                header.append(line).append("\n");
            }

            String output = "HTTP/1.1 200 OK\r\n\r\nResponse OK\n\n"+header;

            if(!"GET /req HTTP/1.1".equals(line) && Math.random() < 0.1716282703815588d){
                output = "HTTP/1.1 500 OK\r\n\r\nResponse NOK\n\n"+header;
            }
            clientSocket.getOutputStream().write(output.getBytes(StandardCharsets.UTF_8));
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
