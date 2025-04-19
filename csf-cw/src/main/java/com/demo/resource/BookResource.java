package com.demo.resource;

import com.demo.model.Book;
import com.demo.dataModel.BookDataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {



    // ✅ Get book by ISBN
    @GET
    @Path("/{isbn}")
    public Response getBookByISBN(@PathParam("isbn") String isbn) {
        Book book = BookDataStore.getBook(isbn);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found\"}").build();
        }
        return Response.ok(book).build();
    }

    @POST
    @Path("/addBook")  // Correct endpoint for adding a book
    public Response addBook(Book book) {
        if (book == null || book.getISBN() == null || book.getTitle() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid book details\"}").build();
        }

        BookDataStore.addBook(book);

        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Book added successfully\"}").build();
    }



    // ✅ Update a book
    @PUT
    @Path("/{isbn}")
    public Response updateBook(@PathParam("isbn") String isbn, Book updatedBook) {
        Book original = BookDataStore.getBook(isbn);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found\"}").build();
        }

        BookDataStore.updateBook(
                original.getTitle(), original.getAuthor(), original.getISBN(),
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getISBN(),
                updatedBook.getPublicationYear(), updatedBook.getPrice(), updatedBook.getStockQuantity()
        );

        return Response.ok("{\"message\":\"Book updated successfully\"}").build();
    }

    // ✅ Delete a book
    @DELETE
    @Path("/{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) {
        Book book = BookDataStore.getBook(isbn);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found\"}").build();
        }

        BookDataStore.removeBook(book.getTitle(), book.getAuthor(), book.getISBN());
        return Response.ok("{\"message\":\"Book deleted successfully\"}").build();
    }
}
