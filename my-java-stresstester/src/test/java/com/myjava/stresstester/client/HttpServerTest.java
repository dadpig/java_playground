package com.myjava.stresstester.client;

import com.myjava.stresstester.server.HttpServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpServerTest {

    private static  HttpServer server;
    private Thread serverThread;


    @BeforeAll
    public static void setup() throws InterruptedException {
        server = new HttpServer(8080);
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


    @Test
    public void testStressHandler(){
        AtomicInteger countSuccess = new AtomicInteger( 0);
        AtomicInteger countFailures = new AtomicInteger( 0);
        AtomicInteger countRequests = new AtomicInteger( 0);
        int threadCount = 10;
        int callCountPerThread =  100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < callCountPerThread; j++) {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/"))
                            .build();

                    HttpResponse<String> response = null;
                    try {
                        response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        countRequests.incrementAndGet();
                        if(200 == response.statusCode()){
                            countSuccess.incrementAndGet();
                        }else{
                            countFailures.incrementAndGet();
                        }
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

        }

        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            countFailures.incrementAndGet();
            executor.shutdownNow();

        }
        //executor.shutdown();

        System.out.println("Total requests: "+countRequests.intValue());
        System.out.println("Total success:"+countSuccess.intValue());
        System.out.println("Total failures: "+countFailures.intValue());

        Assertions.assertEquals(1000, countRequests.intValue());
    }

}
