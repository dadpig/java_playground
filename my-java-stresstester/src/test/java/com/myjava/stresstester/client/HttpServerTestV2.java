package com.myjava.stresstester.client;

import com.myjava.stresstester.server.HttpServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicInteger;


public class HttpServerTestV2 {
    private static  HttpServer server;
    private static final String API_URL = "http://localhost:8080/"; // Replace with actual API endpoint
    private static final int MAX_REQUESTS = 10;
    private static final Duration RAMP_UP_INTERVAL = Duration.ofMillis(200);

    private static AtomicInteger totalRequests = new AtomicInteger(0);
    private static AtomicInteger successfulRequests = new AtomicInteger(0);
    private static AtomicInteger failedRequests = new AtomicInteger(0);
    private static List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());

    @Test
    public void stressTest() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            for (int i = 1; i <= MAX_REQUESTS; i++) {

                int concurrentRequest = i;
                // Wait between ramp-up intervals
                Thread.sleep(RAMP_UP_INTERVAL.toMillis());

                // Submit the tasks
                scope.fork(() -> {
                    // Ramp-up: increase the number of requests every RAMP_UP_INTERVAL
                   for (int j = 0; j < concurrentRequest; j++) {
                        sendHttpRequest(client);
                   }
                    return null;
                });
            }

            scope.join();
        }

        // Calculate statistics
        long p99 = calculateP99(responseTimes);
        System.out.println(STR."Total Requests: \{totalRequests.get()}");
        System.out.println(STR."Successful Requests: \{successfulRequests.get()}");
        System.out.println(STR."Failed Requests: \{failedRequests.get()}");
        System.out.println(STR."P99 Response Time: \{p99} ms");
    }

    private static void sendHttpRequest(HttpClient client) {
        try {
            //add +1 request
            totalRequests.incrementAndGet();
            //get current time
            Instant start = Instant.now();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .timeout(Duration.ofSeconds(1)) // Set a reasonable timeout
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Instant end = Instant.now();
            long responseTime = Duration.between(start, end).toMillis();
            responseTimes.add(responseTime);

            if (response.statusCode() == 200) {
                successfulRequests.incrementAndGet();
            } else {
                failedRequests.incrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
            failedRequests.incrementAndGet();
        }
    }

    private static long calculateP99(List<Long> times) {
        if (times.isEmpty()) {
            return 0;
        }

        List<Long> sortedTimes = times.stream().sorted().toList();
        int index = (int) Math.ceil(0.99 * sortedTimes.size()) - 1;
        return sortedTimes.get(index);
    }


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
}
