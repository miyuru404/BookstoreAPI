package com.demo.exception.exceptionMapper;

import com.demo.exception.CustomerNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;


public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {
    @Override
    public Response toResponse(CustomerNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .build();
    }
}



//AuthorNotFoundExceptionMapper	404 Not Found
//InvalidInputExceptionMapper	400 Bad Request
//OutOfStockExceptionMapper 	409 Conflict
//CartNotFoundExceptionMapper 	404 Not Found
