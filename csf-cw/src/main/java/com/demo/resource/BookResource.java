package com.demo.resource;



import com.demo.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    @Path("/{title}")
    public Response getBook(@PathParam("title") String title) {
        // Example: returns a dummy book
        Book book = new Book("hi","miyuru","123456",2023,2000.00,5);
        return Response.ok(book).build();
    }

    @POST
    public Response addBook(Book book) {
        return Response.ok("Book added: " + book.getTitle()).build();
    }
}
