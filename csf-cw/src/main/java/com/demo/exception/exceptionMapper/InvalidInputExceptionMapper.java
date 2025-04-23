package com.demo.exception.exceptionMapper;

import com.demo.exception.InvalidInputException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {
    @Override
    public Response toResponse(InvalidInputException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}
