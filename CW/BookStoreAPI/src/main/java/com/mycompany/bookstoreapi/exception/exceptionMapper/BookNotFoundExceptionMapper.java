package com.mycompany.bookstoreapi.exception.exceptionMapper;


import com.mycompany.bookstoreapi.exception.BookNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    @Override
    public Response toResponse(BookNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}
