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


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAuthor(Author author) {
        if (author == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Author details are missing.\"}").build();
        }

        if (author.getName() == null || author.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Name is required.\"}").build();
        }

        if (author.getSurname() == null || author.getSurname().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Surname is required.\"}").build();
        }

        if (!author.getName().matches("^[A-Za-z ]+$") || !author.getSurname().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}")
                    .build();
        }

        if (author.getAge() < 1 || author.getAge() > 100) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid age. Age must be between 0 and 100.\"}").build();
        }
        if (AuthorDataStore.getAuthor(author.getName(),author.getSurname()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Author with this name is already registered.\"}")
                    .build();
        }


        AuthorDataStore.addAuthor(author);


        return Response.status(Response.Status.CREATED)
                .entity("{\"error\":\"Author successfully added.\"}")
                .build();
    }

    @DELETE
    @Path("/delete/{name}-{surname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCustomer(@PathParam("name") String name, @PathParam("surname") String surname) {
        if (name == null || name.trim().isEmpty() ) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Name is required.\"}")
                    .build();
        }
        if ( surname == null || surname.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Surname is required.\"}")
                    .build();
        }


        Author author = AuthorDataStore.getAuthor(name, surname);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found.\"}")
                    .build();
        }

        AuthorDataStore.deleteAuthor(name, surname);
        return Response.status(Response.Status.OK)
                .entity("{\"error\":\"Customer removed successfully.\"}")
                .build();
    }

    @PUT
    @Path("/update/{name}-{surname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("name") String name, @PathParam("surname") String surname, Author updatedAuthor) {
        if (updatedAuthor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Updated customer object is missing.\"}")
                    .build();
        }

        if (updatedAuthor.getName() == null || updatedAuthor.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"New name is required\"}")
                    .build();
        }

        if (!updatedAuthor.getName().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Only letters allowed.\"}")
                    .build();
        }

        if (updatedAuthor.getSurname() == null || updatedAuthor.getSurname().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"New surname is required.\"}")
                    .build();
        }

        if (!updatedAuthor.getSurname().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid surname. Only letters  allowed.\"}")
                    .build();
        }

        if (updatedAuthor.getAge() < 1 || updatedAuthor.getAge() > 100) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid age. Age must be between 0 and 100.\"}").build();
        }
        if (AuthorDataStore.getAuthor(updatedAuthor.getName(),updatedAuthor.getSurname()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Author with this name is already registered.\"}")
                    .build();
        }
        Author existingAuthor = AuthorDataStore.getAuthor(name, surname);
        if (existingAuthor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\" Author not found.\"}")
                    .build();
        }

        AuthorDataStore.updateAuthor(name, surname, updatedAuthor);
        return Response.status(Response.Status.OK)
                .entity("{\"error\":\"Author updated successfully.\"}")
                .build();
    }




}
