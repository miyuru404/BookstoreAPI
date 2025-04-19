package com.demo;



import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/api/";

    public static HttpServer startServer() {
        // Scan for JAX-RS resources in this package
        final ResourceConfig rc = new ResourceConfig().packages("com.demo.resource");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Server started at " + BASE_URI);
        System.out.println("Visit: http://localhost:8080/api/books/JavaBook");

        try {
            System.in.read(); // Press Enter to stop server
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.shutdownNow();
    }
}
