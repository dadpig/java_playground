package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpServerTest {

    //private HttpServer server;
    private Thread serverThread;
    @BeforeEach
    public void setup() throws InterruptedException {
        final HttpServer server = new HttpServer(8080);
        Thread serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        serverThread.start();
        Thread.sleep(1000);
    }

    @Test
    public void testHandler() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/req"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(200, response.statusCode());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
