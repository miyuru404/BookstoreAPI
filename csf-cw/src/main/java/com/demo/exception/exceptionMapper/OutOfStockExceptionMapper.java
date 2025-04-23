package com.demo.exception.exceptionMapper;

import com.demo.exception.OutOfStockException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    @Override
    public Response toResponse(OutOfStockException ex) {
        return Response.status(Response.Status.CONFLICT)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}
