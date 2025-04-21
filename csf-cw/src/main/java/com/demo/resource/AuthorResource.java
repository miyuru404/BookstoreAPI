package com.demo.resource;

import com.demo.dataModel.AuthorDataStore;
import com.demo.model.Author;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;




@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @GET
    @Path("/name/{name}-{surname}")
    public Response getAuthorByName(@PathParam("name") String name, @PathParam("surname") String surname) {
        if (name == null || name.isEmpty() || surname == null || surname.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Author full name is missing\"}").build();
        }

        if (!name.matches("^[A-Za-z ]+$") || !surname.matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}")
                    .build();
        }

        Author author = AuthorDataStore.getAuthor(name, surname);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Author not found\"}").build();
        }

        return Response.ok(author).build();
    }


}
