package com.demo.app;

import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")  // Base URL path for all your endpoints
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        // Register the package where your resources are defined
        packages("com.demo.resource"); // Replace with your actual package
    }
}
