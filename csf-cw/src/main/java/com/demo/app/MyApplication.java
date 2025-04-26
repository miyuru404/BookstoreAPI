package com.demo.app;


import com.demo.exception.exceptionMapper.*;
import com.demo.resource.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api") // Set the base URI for the API
public class MyApplication extends Application {

    // Override the getClasses method to register your resources and exception mappers
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();




        // Register resources (like your BookResource)
        resources.add(BookResource.class);
        resources.add(AuthorResource.class);
        resources.add(CartResource.class);
        resources.add(CustomerResource.class);
        resources.add(OrderResource.class);
        resources.add(JacksonFeature.class);

        // Register exception mappers
        resources.add(BookNotFoundExceptionMapper.class);
        resources.add(AuthorNotFoundExceptionMapper.class);
        resources.add(CartNotFoundExceptionMapper.class);
        resources.add(InvalidInputExceptionMapper.class);
        resources.add(OrderNotFoundExceptionMapper.class);
        resources.add(OutOfStockExceptionMapper.class);
        resources.add(CustomerNotFoundExceptionMapper.class);

        // Optionally, you can add other resources, filters, etc. here
        // Example: resources.add(MyOtherResource.class);

        return resources;
    }
}
