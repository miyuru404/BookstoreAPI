package com.demo.resource;

import com.demo.dataModel.AuthorDataStore;
import com.demo.exception.AuthorNotFoundException;
import com.demo.exception.InvalidInputException;
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
            throw new InvalidInputException("Author full name is missing");
        }

        if (!name.matches("^[A-Za-z ]+$") || !surname.matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid name. Name should contain only letters and spaces.");
        }

        Author author = AuthorDataStore.getAuthor(name, surname);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found");
        }

        return Response.ok(author).build();
    }

    @POST
    @Path("/add")
    public Response addAuthor(Author author) {
        if (author == null) {
            throw new InvalidInputException("Author details are missing.");
        }

        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Name is required.");
        }

        if (author.getSurname() == null || author.getSurname().trim().isEmpty()) {
            throw new InvalidInputException("Surname is required.");
        }

        if (!author.getName().matches("^[A-Za-z ]+$") || !author.getSurname().matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid name. Name should contain only letters and spaces.");
        }

        if (author.getAge() < 1 || author.getAge() > 100) {
            throw new InvalidInputException("Invalid age. Age must be between 0 and 100.");
        }

        if (AuthorDataStore.getAuthor(author.getName(), author.getSurname()) != null) {
            throw new InvalidInputException("Author with this name is already registered.");
        }

        AuthorDataStore.addAuthor(author);

        return Response.status(Response.Status.CREATED)
                .entity("{\"error\":\"Author successfully added.\"}")
                .build();
    }

    @DELETE
    @Path("/delete/{name}-{surname}")
    public Response removeCustomer(@PathParam("name") String name, @PathParam("surname") String surname) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name is required.");
        }

        if (surname == null || surname.trim().isEmpty()) {
            throw new InvalidInputException("Surname is required.");
        }

        Author author = AuthorDataStore.getAuthor(name, surname);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found.");
        }

        AuthorDataStore.deleteAuthor(name, surname);
        return Response.status(Response.Status.OK)
                .entity("{\"error\":\"Author removed successfully.\"}")
                .build();
    }

    @PUT
    @Path("/update/{name}-{surname}")
    public Response updateCustomer(@PathParam("name") String name, @PathParam("surname") String surname, Author updatedAuthor) {
        if (updatedAuthor == null) {
            throw new InvalidInputException("Updated Author object is missing.");
        }

        if (updatedAuthor.getName() == null || updatedAuthor.getName().trim().isEmpty()) {
            throw new InvalidInputException("New name is required.");
        }

        if (!updatedAuthor.getName().matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid name. Only letters allowed.");
        }

        if (updatedAuthor.getSurname() == null || updatedAuthor.getSurname().trim().isEmpty()) {
            throw new InvalidInputException("New surname is required.");
        }

        if (!updatedAuthor.getSurname().matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid surname. Only letters allowed.");
        }

        if (updatedAuthor.getAge() < 1 || updatedAuthor.getAge() > 100) {
            throw new InvalidInputException("Invalid age. Age must be between 0 and 100.");
        }

        if (AuthorDataStore.getAuthor(updatedAuthor.getName(), updatedAuthor.getSurname()) != null) {
            throw new InvalidInputException("Author with this name is already registered.");
        }

        Author existingAuthor = AuthorDataStore.getAuthor(name, surname);
        if (existingAuthor == null) {
            throw new AuthorNotFoundException("Author not found.");
        }

        AuthorDataStore.updateAuthor(name, surname, updatedAuthor);
        return Response.status(Response.Status.OK)
                .entity("{\"error\":\"Author updated successfully.\"}")
                .build();
    }

}
