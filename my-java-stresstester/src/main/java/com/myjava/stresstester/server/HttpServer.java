package com.myjava.stresstester.server;

import com.myjava.stresstester.client.Request;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {
    private int port;
    private ServerSocket serverSocket;
    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try {

            serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
            System.out.println("Server started");
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                if(clientSocket.isConnected()){
                    Thread.ofVirtual().start(new Request(clientSocket));
                }
            }
        } catch (IOException e) {
            System.out.println("Error "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public  void stop(){
        if(serverSocket.isBound()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}