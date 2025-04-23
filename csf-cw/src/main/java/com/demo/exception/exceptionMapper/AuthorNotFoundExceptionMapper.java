package com.demo.exception.exceptionMapper;

import com.demo.exception.AuthorNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {
    @Override
    public Response toResponse(AuthorNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}
