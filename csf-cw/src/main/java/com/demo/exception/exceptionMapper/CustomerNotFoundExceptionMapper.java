package com.demo.exception.exceptionMapper;

import com.demo.exception.CustomerNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {
    @Override
    public Response toResponse(CustomerNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}




