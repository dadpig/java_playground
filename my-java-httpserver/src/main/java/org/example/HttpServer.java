package org.example;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try {

            final ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
            System.out.println("Server started");
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                if(clientSocket.isConnected()){
                    System.out.println("Connection accepted");
                    Thread.ofVirtual().start(new Request(clientSocket));
                }
            }
        } catch (IOException e) {
            System.out.println("Error "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

}